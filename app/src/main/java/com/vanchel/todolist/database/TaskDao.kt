package com.vanchel.todolist.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Transaction
    @Query("SELECT * FROM topics")
    fun getTopicsWithTasks(): Flow<List<TopicWithTasks>>
}