package com.vanchel.todolist.domain

import java.util.*

data class Task(
    val title: String,
    val completed: Boolean,
    val id: UUID = UUID.randomUUID()
)
