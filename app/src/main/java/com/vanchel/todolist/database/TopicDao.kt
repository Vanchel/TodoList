package com.vanchel.todolist.database

import androidx.room.*

@Dao
interface TopicDao {
    @Insert
    suspend fun addTopic(topic: TopicEntity)

    @Update
    suspend fun updateTopic(topic: TopicEntity)

    @Delete
    suspend fun deleteTopic(topic: TopicEntity)

    @Query("SELECT * FROM topics")
    suspend fun getTopics(): List<TopicEntity>
}