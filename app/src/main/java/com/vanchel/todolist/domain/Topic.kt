package com.vanchel.todolist.domain

import java.util.*

data class Topic(
    val name: String,
    val id: UUID = UUID.randomUUID()
)
