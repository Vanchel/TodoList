package com.vanchel.todolist.database

import androidx.room.*

interface TaskDao {
    @Insert
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Transaction
    @Query("SELECT * FROM topics")
    suspend fun getTopicsWithTasks(): List<TopicWithTasks>
}