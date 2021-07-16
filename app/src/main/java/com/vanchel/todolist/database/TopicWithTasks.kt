package com.vanchel.todolist.database

import androidx.room.Embedded
import androidx.room.Relation
import com.vanchel.todolist.domain.TaskList
import com.vanchel.todolist.domain.Topic

data class TopicWithTasks(
    @Embedded val topic: TopicEntity,
    @Relation(
        parentColumn = "topic_id",
        entityColumn = "topic_id"
    )
    val tasks: List<TaskEntity>
) {
    fun toTaskListModel() = TaskList(
        topic = topic.toTopicModel(),
        tasks = tasks.map(TaskEntity::toTaskModel)
    )
}