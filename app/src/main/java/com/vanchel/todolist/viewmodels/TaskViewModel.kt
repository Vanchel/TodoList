package com.vanchel.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.vanchel.todolist.database.getTodoDatabase
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.repository.TodoRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository(getTodoDatabase(application))

    private val _selectedTopic = MutableLiveData<Topic>()

    val topics = todoRepository.topics.asLiveData()

    val isTopicSelected
        get() = _selectedTopic.value != null

    fun selectTopic(topic: Topic) {
        _selectedTopic.value = topic
    }

    fun addTask(title: String) {
        _selectedTopic.value?.let {
            viewModelScope.launch {
                val newTask = Task(title, false)
                todoRepository.addTask(newTask, it)
            }
        }
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