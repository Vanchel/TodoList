package com.vanchel.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.vanchel.todolist.database.getTodoDatabase
import com.vanchel.todolist.repository.TodoRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository(getTodoDatabase(application))

    val taskLists = todoRepository.taskLists.asLiveData()

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(application) as T
            }
            throw IllegalAccessException("unable to construct viewModel")
        }
    }
}