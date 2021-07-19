package com.vanchel.todolist.repository

import com.vanchel.todolist.database.TodoDatabase
import com.vanchel.todolist.database.TopicEntity
import com.vanchel.todolist.database.TopicWithTasks
import com.vanchel.todolist.domain.TaskList
import com.vanchel.todolist.domain.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepository(private val database: TodoDatabase) {
    val taskLists = database.taskDao().getTopicsWithTasks()
        .map {
            it.map(TopicWithTasks::toTaskListModel)
        }

    val topics = database.topicDao().getTopics()
        .map {
            it.map(TopicEntity::toTopicModel)
        }

    suspend fun addTopic(topic: Topic) {
        val topicEntity = TopicEntity.fromTopicModel(topic)
        database.topicDao().addTopic(topicEntity)
    }

    suspend fun deleteTopic(topic: Topic) {
        val topicEntity = TopicEntity.fromTopicModel(topic)
        database.topicDao().deleteTopic(topicEntity)
    }
}