package com.vanchel.todolist.database

import androidx.room.Embedded
import androidx.room.Relation
import com.vanchel.todolist.domain.Topic

data class TopicWithTasks(
    @Embedded val topic: TopicEntity,
    @Relation(
        parentColumn = "topic_id",
        entityColumn = "topic_id"
    )
    val tasks: List<TaskEntity>
) {
    fun toTopicModel() = Topic(
        name = topic.name,
        tasks = tasks.map(TaskEntity::toTaskModel)
    )
}