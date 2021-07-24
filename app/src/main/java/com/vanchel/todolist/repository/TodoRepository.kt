package com.vanchel.todolist.repository

import com.vanchel.todolist.database.TaskEntity
import com.vanchel.todolist.database.TodoDatabase
import com.vanchel.todolist.database.TopicEntity
import com.vanchel.todolist.database.TopicWithTasks
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.TaskList
import com.vanchel.todolist.domain.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class TodoRepository(private val database: TodoDatabase) {
    val topics = database.topicDao().getTopics()
        .map {
            it.map(TopicEntity::toTopicModel)
        }

    val taskLists = database.taskDao().getTopicsWithTasks()
        .map {
            it.map(TopicWithTasks::toTaskListModel)
        }

    suspend fun addTopic(topic: Topic) {
        val topicEntity = TopicEntity.fromTopicModel(topic)
        database.topicDao().addTopic(topicEntity)
    }

    suspend fun deleteTopic(topic: Topic) {
        val topicEntity = TopicEntity.fromTopicModel(topic)
        database.topicDao().deleteTopic(topicEntity)
    }

    suspend fun addTask(task: Task, topic: Topic) {
        val taskEntity = TaskEntity.fromTaskModel(task, topic)
        database.taskDao().addTask(taskEntity)
    }

    suspend fun updateTask(task: Task, topic: Topic) {
        val taskEntity = TaskEntity.fromTaskModel(task, topic)
        database.taskDao().updateTask(taskEntity)
    }

    fun getTaskList(topicId: UUID): Flow<TaskList> {
        val topicWithTasks = database.taskDao().getTopicWithTasks(topicId)
        return topicWithTasks.map(TopicWithTasks::toTaskListModel)
    }
}