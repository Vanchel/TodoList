package com.vanchel.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vanchel.todolist.databinding.ListItemTopicDeleteBinding
import com.vanchel.todolist.domain.Topic

class TopicDeleteAdapter(private val onDelete: (topic: Topic) -> Unit) :
    ListAdapter<Topic, TopicDeleteAdapter.ViewHolder>(TopicDeleteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDelete)
    }

    class ViewHolder private constructor(private val binding: ListItemTopicDeleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Topic, onDelete: (topic: Topic) -> Unit) {
            binding.apply {
                topic = item
                deleteButton.setOnClickListener {
                    onDelete.invoke(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTopicDeleteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TopicDeleteDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.name == newItem.name
    }
}