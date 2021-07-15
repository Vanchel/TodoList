package com.vanchel.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TopicEntity::class, TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TodoDao
}

private lateinit var instance: TaskDatabase

fun getInstance(context: Context): TaskDatabase {
    synchronized(TaskDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "todo-db"
            ).build()
        }
    }
    return instance
}