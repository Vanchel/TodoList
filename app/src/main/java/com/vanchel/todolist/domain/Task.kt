package com.vanchel.todolist.domain

import java.util.*

data class Task(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val completed: Boolean
)
