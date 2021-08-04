package com.vanchel.todolist.di

import com.vanchel.todolist.repository.TodoRepository
import com.vanchel.todolist.repository.TodoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindTodoRepository(todoRepositoryImpl: TodoRepositoryImpl) : TodoRepository
}