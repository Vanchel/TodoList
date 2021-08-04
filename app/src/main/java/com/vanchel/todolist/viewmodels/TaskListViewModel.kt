package com.vanchel.todolist.viewmodels

import androidx.lifecycle.*
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.repository.TodoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.*

class TaskListViewModel @AssistedInject constructor(
    private val todoRepository: TodoRepository,
    @Assisted topicId: UUID
) : ViewModel() {
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

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            topicId: UUID
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                    return assistedFactory.create(topicId) as T
                }
                throw IllegalAccessException("unable to construct viewModel")
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(topicId: UUID): TaskListViewModel
    }
}