package com.vanchel.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vanchel.todolist.databinding.ListItemTaskListBinding
import com.vanchel.todolist.domain.TaskList

class MainAdapter : ListAdapter<TaskList, MainAdapter.ViewHolder>(MainDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ListItemTaskListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskList) {
            binding.taskList = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTaskListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class MainDiffCallback : DiffUtil.ItemCallback<TaskList>() {
    override fun areItemsTheSame(oldItem: TaskList, newItem: TaskList): Boolean {
        return oldItem.topic.id == newItem.topic.id
    }

    override fun areContentsTheSame(oldItem: TaskList, newItem: TaskList): Boolean {
        /* Due to the fact that a task can only be completed or uncompleted, there is no need to
        count the number of values that satisfy each of these conditions separately. It is
        enough to check only one of them and just compare the lengths of the lists. */

        return oldItem.topic.name == newItem.topic.name &&
                oldItem.tasks.size == newItem.tasks.size &&
                oldItem.tasks.count { it.completed } == newItem.tasks.count { it.completed }
    }

}