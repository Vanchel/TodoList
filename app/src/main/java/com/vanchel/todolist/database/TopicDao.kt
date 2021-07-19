package com.vanchel.todolist.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert
    suspend fun addTopic(topic: TopicEntity)

    @Update
    suspend fun updateTopic(topic: TopicEntity)

    @Delete
    suspend fun deleteTopic(topic: TopicEntity)

    @Query("SELECT * FROM topics")
    fun getTopics(): Flow<List<TopicEntity>>
}