package com.vanchel.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.vanchel.todolist.database.getTodoDatabase
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.repository.TodoRepository
import kotlinx.coroutines.launch

class TopicViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository(getTodoDatabase(application))

    val topics = todoRepository.topics.asLiveData()

    /* It may be a dubious decision to perform these operations in the view model scope,
    since the dialog fragment is closed immediately after such an operation is called.
    Under such conditions, the operation itself may not have time to complete in the coroutine
    before the destruction of the view model and therefore be interrupted.
    Have to check and test. */

    fun addTopic(name: String) {
        viewModelScope.launch {
            val newTopic = Topic(name)
            todoRepository.addTopic(newTopic)
        }
    }

    fun deleteTopic(topic: Topic) {
        viewModelScope.launch {
            todoRepository.deleteTopic(topic)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(TopicViewModel::class.java)) {
                return TopicViewModel(application) as T
            }
            throw IllegalAccessException("unable to construct viewModel")
        }
    }
}