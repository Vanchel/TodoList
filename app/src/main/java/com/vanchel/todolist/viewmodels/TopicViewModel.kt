package com.vanchel.todolist.viewmodels

import androidx.lifecycle.*
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {
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
}