package com.vanchel.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanchel.todolist.domain.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id") val taskId: Long,
    @ColumnInfo(name = "topic_id") val topicId: Long,
    val title: String,
    val completed: Boolean
) {
    fun toTaskModel() = Task(
        title = title,
        completed = completed
    )
}