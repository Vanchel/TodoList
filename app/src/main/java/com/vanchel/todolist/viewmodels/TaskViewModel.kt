package com.vanchel.todolist.viewmodels

import androidx.lifecycle.*
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {
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
}