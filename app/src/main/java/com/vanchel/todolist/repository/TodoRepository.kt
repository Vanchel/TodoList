package com.vanchel.todolist.repository

import com.vanchel.todolist.database.TodoDatabase
import com.vanchel.todolist.database.TopicWithTasks
import com.vanchel.todolist.domain.TaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepository(database: TodoDatabase) {
    val taskLists = database.taskDao().getTopicsWithTasks()
        .map {
            it.map(TopicWithTasks::toTaskListModel)
        }
}