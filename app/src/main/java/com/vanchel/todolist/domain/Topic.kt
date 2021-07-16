package com.vanchel.todolist.domain

import java.util.*

data class Topic(
    val id: UUID = UUID.randomUUID(),
    val name: String
)
