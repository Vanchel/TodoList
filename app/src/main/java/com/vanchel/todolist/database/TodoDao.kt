package com.vanchel.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TodoDao {
    @Transaction
    @Query("SELECT * FROM topics")
    suspend fun getTopicsWithTasks(): List<TopicWithTasks>
}