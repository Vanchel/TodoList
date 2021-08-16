package com.vanchel.todolist.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.core.Every
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

/**
 * I have not been able to fully figure out for what specific reason, but runBlockingTest() does
 * not work and throws an exception. runBlocking(), although I kind of shouldn't be using it,
 * works as it should.
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TodoDatabaseTest {
    private lateinit var topicDao: TopicDao
    private lateinit var taskDao: TaskDao
    private lateinit var db: TodoDatabase

    private suspend fun prepareTopics(amount: Int): List<TopicEntity> {
        require(amount >= 0)
        val topics = List(amount) { TopicEntity(UUID.randomUUID(), it.toString()) }
        topics.forEach { topicDao.addTopic(it) }
        return topics
    }

    private suspend fun prepareTopicWithTasks(amount: Int): TopicWithTasks {
        require(amount >= 0)
        val topic = TopicEntity(UUID.randomUUID(), "topic")
        topicDao.addTopic(topic)
        val tasks = List(amount) {
            TaskEntity(UUID.randomUUID(), topic.topicId, it.toString(), completed = false)
        }
        tasks.forEach { taskDao.addTask(it) }
        return TopicWithTasks(topic, tasks)
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java).build()
        topicDao = db.topicDao()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun addTopic_addsTopic() = runBlocking {
        val newTopic = TopicEntity(UUID.randomUUID(), "topic")

        topicDao.addTopic(newTopic)
        val newFromDb = topicDao.getTopics().first()[0]

        assertThat(newTopic, `is`(newFromDb))
    }

    @Test
    fun updateTopic_updatesTopic() = runBlocking {
        val initialTopic = TopicEntity(UUID.randomUUID(), "topic")
        topicDao.addTopic(initialTopic)
        val initialFromDb = topicDao.getTopics().first()[0]

        val updatedTopic = initialTopic.copy(name = "updated")
        topicDao.updateTopic(updatedTopic)
        val updatedFromDb = topicDao.getTopics().first()[0]

        assertThat(initialFromDb.topicId, `is`(updatedFromDb.topicId))
        assertThat(initialFromDb, not(updatedFromDb))
    }

    @Test
    fun deleteTopic_deletesTopic() = runBlocking {
        val topics = prepareTopics(3)

        val topicToDelete = topics[1]
        topicDao.deleteTopic(topicToDelete)
        val topicsFromDb = topicDao.getTopics().first()

        assertThat(topicsFromDb, Every.everyItem(not(topicToDelete)))
    }

    @Test
    fun getTopics_getsTopics() = runBlocking {
        val topics = prepareTopics(3)

        val topicsFromDb = topicDao.getTopics().first()

        assertThat(topicsFromDb, `is`(topics))
    }

    @Test
    fun addTask_addsTask() = runBlocking {
        val topic = TopicEntity(UUID.randomUUID(), "topic")
        topicDao.addTopic(topic)
        val newTask = TaskEntity(UUID.randomUUID(), topic.topicId, "task", false)

        taskDao.addTask(newTask)
        val newFromDb = taskDao.getTopicsWithTasks().first()[0].tasks[0]

        assertThat(newTask, `is`(newFromDb))
    }

    @Test
    fun updateTask_updatesTask() = runBlocking {
        val topic = TopicEntity(UUID.randomUUID(), "topic")
        topicDao.addTopic(topic)
        val initialTask = TaskEntity(UUID.randomUUID(), topic.topicId, "task", false)
        taskDao.addTask(initialTask)
        val initialFromDb = taskDao.getTopicsWithTasks().first()[0].tasks[0]
        val updatedTask = initialTask.copy(completed = true)
        taskDao.updateTask(updatedTask)
        val updatedFromDb = taskDao.getTopicsWithTasks().first()[0].tasks[0]

        assertThat(initialFromDb.taskId, `is`(updatedFromDb.taskId))
        assertThat(initialFromDb, not(updatedFromDb))
    }

    @Test
    fun deleteTask_deletesTask() = runBlocking {
        val tasks = prepareTopicWithTasks(3).tasks

        val taskToDelete = tasks[1]
        taskDao.deleteTask(taskToDelete)
        val tasksFromDb = taskDao.getTopicsWithTasks().first()[0].tasks

        assertThat(tasksFromDb, Every.everyItem(not(taskToDelete)))
    }

    @Test
    fun getTopicsWithTasks_getsTopicsWithTasks() = runBlocking {
        val topicsWithTasks = listOf(
            prepareTopicWithTasks(3),
            prepareTopicWithTasks(5),
            prepareTopicWithTasks(0)
        )

        val topicsWithTasksFromDb = taskDao.getTopicsWithTasks().first()

        assertThat(topicsWithTasksFromDb, `is`(topicsWithTasks))
    }

    @Test
    fun getTopicWithTasks_getsTopicWithTasks() = runBlocking {
        val topicWithTasks = prepareTopicWithTasks(3)

        val topicWithTasksFromDb =
            taskDao.getTopicWithTasks(topicWithTasks.topic.topicId).first()

        assertThat(topicWithTasksFromDb, `is`(topicWithTasks))
    }
}