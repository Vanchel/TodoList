package com.vanchel.todolist.repository

import com.vanchel.todolist.database.*
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.TaskList
import com.vanchel.todolist.domain.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

interface TodoRepository {
    val topics: Flow<List<Topic>>

    val taskLists: Flow<List<TaskList>>

    suspend fun addTopic(topic: Topic)

    suspend fun deleteTopic(topic: Topic)

    suspend fun addTask(task: Task, topic: Topic)

    suspend fun updateTask(task: Task, topic: Topic)

    suspend fun deleteTask(task: Task, topic: Topic)

    fun getTaskList(topicId: UUID): Flow<TaskList>
}

class TodoRepositoryImpl @Inject constructor(
    private val topicDao: TopicDao,
    private val taskDao: TaskDao
) : TodoRepository {
    override val topics
        get() = topicDao.getTopics()
            .map {
                it.map(TopicEntity::toTopicModel)
            }

    override val taskLists
        get() = taskDao.getTopicsWithTasks()
            .map {
                it.map(TopicWithTasks::toTaskListModel)
            }

    override suspend fun addTopic(topic: Topic) {
        val topicEntity = TopicEntity.fromTopicModel(topic)
        topicDao.addTopic(topicEntity)
    }

    override suspend fun deleteTopic(topic: Topic) {
        val topicEntity = TopicEntity.fromTopicModel(topic)
        topicDao.deleteTopic(topicEntity)
    }

    override suspend fun addTask(task: Task, topic: Topic) {
        val taskEntity = TaskEntity.fromTaskModel(task, topic)
        taskDao.addTask(taskEntity)
    }

    override suspend fun updateTask(task: Task, topic: Topic) {
        val taskEntity = TaskEntity.fromTaskModel(task, topic)
        taskDao.updateTask(taskEntity)
    }

    override suspend fun deleteTask(task: Task, topic: Topic) {
        val taskEntity = TaskEntity.fromTaskModel(task, topic)
        taskDao.deleteTask(taskEntity)
    }

    override fun getTaskList(topicId: UUID): Flow<TaskList> {
        val topicWithTasks = taskDao.getTopicWithTasks(topicId)
        return topicWithTasks.map(TopicWithTasks::toTaskListModel)
    }
}