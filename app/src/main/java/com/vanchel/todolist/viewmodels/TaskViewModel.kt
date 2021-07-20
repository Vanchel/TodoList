package com.vanchel.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.vanchel.todolist.database.getTodoDatabase
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.repository.TodoRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository(getTodoDatabase(application))

    private var selectedTopic: Topic? = null

    val topics = todoRepository.topics.asLiveData()

    fun selectTopic(topic: Topic) {
        selectedTopic = topic
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(application) as T
            }
            throw IllegalAccessException("unable to construct viewModel")
        }
    }
}