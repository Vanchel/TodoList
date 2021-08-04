package com.vanchel.todolist.di

import android.content.Context
import com.vanchel.todolist.database.TaskDao
import com.vanchel.todolist.database.TodoDatabase
import com.vanchel.todolist.database.TopicDao
import com.vanchel.todolist.database.getTodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return getTodoDatabase(context)
    }

    @Provides
    fun provideTopicDao(todoDatabase: TodoDatabase): TopicDao {
        return todoDatabase.topicDao()
    }

    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase): TaskDao {
        return todoDatabase.taskDao()
    }
}