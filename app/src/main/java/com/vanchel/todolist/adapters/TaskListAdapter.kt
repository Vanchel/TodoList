package com.vanchel.todolist.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vanchel.todolist.databinding.ListItemTaskBinding
import com.vanchel.todolist.domain.Task

class TaskListAdapter(private val onComplete: (task: Task) -> Unit) :
    ListAdapter<Task, TaskListAdapter.ViewHolder>(TaskListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onComplete)
    }

    class ViewHolder private constructor(private val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task, onComplete: (task: Task) -> Unit) = with(binding) {
            if (item.completed) {
                taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                root.setOnClickListener(null)
            } else {
                taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                root.setOnClickListener {
                    onComplete.invoke(item)
                }
            }
            task = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTaskBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TaskListDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.title == newItem.title && oldItem.completed == newItem.completed
    }
}