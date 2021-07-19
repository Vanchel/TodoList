package com.vanchel.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanchel.todolist.util.TodoTypeConverters

@Database(
    entities = [TopicEntity::class, TaskEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TodoTypeConverters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun taskDao(): TaskDao
}

private lateinit var instance: TodoDatabase

fun getTodoDatabase(context: Context): TodoDatabase {
    synchronized(TodoDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo-db"
            ).build()
        }
    }
    return instance
}