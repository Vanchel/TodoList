package com.vanchel.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanchel.todolist.domain.Topic
import java.util.*

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey
    @ColumnInfo(name = "topic_id") val topicId: UUID,
    val name: String
) {
    fun toTopicModel() = Topic(
        id = topicId,
        name = name
    )

    companion object {
        fun fromTopicModel(topic: Topic) = TopicEntity(
            topicId = topic.id,
            name = topic.name
        )
    }
}
