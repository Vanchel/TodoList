package com.vanchel.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.vanchel.todolist.database.getTodoDatabase
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.repository.TodoRepository
import kotlinx.coroutines.launch
import java.util.*

class TaskListViewModel(application: Application, topicId: UUID) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository(getTodoDatabase(application))

    val taskList = todoRepository.getTaskList(topicId).asLiveData()

    fun updateTask(task: Task) {
        taskList.value?.let {
            viewModelScope.launch {
                /* Here is an interesting moment in which you have to be careful. You cannot change
                * the property value in a function parameter (and it's good that the property is
                * marked as val in the model), you just need to create a copy. Otherwise, the
                * DiffUtil.ItemCallback will not see the difference between the old element (which
                * we changed) and the new one from the list that was submitted by the observer, so
                * the data in the database will be updated, but UI will not react to this change. */
                val completedTask = task.copy(completed = !task.completed)
                todoRepository.updateTask(completedTask, it.topic)
            }
        }
    }

    fun deleteTask(task: Task) {
        taskList.value?.let {
            viewModelScope.launch {
                todoRepository.deleteTask(task, it.topic)
            }
        }
    }

    class Factory(private val application: Application, private val topicId: UUID) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                return TaskListViewModel(application, topicId) as T
            }
            throw IllegalAccessException("unable to construct viewModel")
        }
    }
}