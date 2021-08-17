package com.vanchel.todolist.database

import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.Topic
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EntitiesTest {
    @Test
    fun topicEntity_convertsFromTopicAndBack_returnsEqualObject() {
        val initialTopic = Topic("topic")

        val topicEntity = TopicEntity.fromTopicModel(initialTopic)
        val topic = topicEntity.toTopicModel()

        assertThat(topic, `is`(initialTopic))
    }

    @Test
    fun taskEntity_convertsFromTaskAndBack_returnsEqualObject() {
        val initialTopic = Topic("topic")
        val initialTask = Task("task", false)

        val taskEntity = TaskEntity.fromTaskModel(initialTask, initialTopic)
        val task = taskEntity.toTaskModel()

        assertThat(task, `is`(initialTask))
    }
}