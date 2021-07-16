package com.vanchel.todolist.util

import androidx.room.TypeConverter
import java.util.*

class TodoTypeConverters {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? = UUID.fromString(uuid)

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()
}