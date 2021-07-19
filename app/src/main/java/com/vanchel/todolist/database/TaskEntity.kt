package com.vanchel.todolist.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.Topic
import java.util.*

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TopicEntity::class,
            parentColumns = ["topic_id"],
            childColumns = ["topic_id"],
            onDelete = CASCADE
        )
    ],
)
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "task_id") val taskId: UUID,
    @ColumnInfo(name = "topic_id", index = true) val topicId: UUID,
    val title: String,
    val completed: Boolean
) {
    fun toTaskModel() = Task(
        id = taskId,
        title = title,
        completed = completed
    )

    companion object {
        fun fromTaskModel(task: Task, topic: Topic) = TaskEntity(
            taskId = task.id,
            topicId = topic.id,
            title = task.title,
            completed = task.completed
        )
    }
}