package com.vanchel.todolist.domain

data class Topic(
    val name: String,
    val tasks: List<Task>
)
