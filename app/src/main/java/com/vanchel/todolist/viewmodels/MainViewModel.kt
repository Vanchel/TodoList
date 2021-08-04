package com.vanchel.todolist.viewmodels

import androidx.lifecycle.*
import com.vanchel.todolist.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(todoRepository: TodoRepository) : ViewModel() {
    val taskLists = todoRepository.taskLists.asLiveData()
}