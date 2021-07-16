package com.vanchel.todolist.domain

data class TaskList(
    val topic: Topic,
    val tasks: List<Task>
)
