package com.vanchel.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vanchel.todolist.databinding.ListItemTopicSelectBinding
import com.vanchel.todolist.domain.Topic

class TopicSelectAdapter(private val onSelected: (topic: Topic) -> Unit) :
    ListAdapter<Topic, TopicSelectAdapter.ViewHolder>(TopicSelectDiffCallback()) {
    private var lastCheckedPosition = -1
    private var lastChecked: ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val isChecked = position == lastCheckedPosition
        val callback = {
            /* Verification is needed here not even to avoid unnecessary code execution,
            but to avoid a bug that would appear when the same button is pressed successively. */

            if (holder != lastChecked) {
                onSelected.invoke(item)
                lastChecked?.uncheck()
                lastChecked = holder
                lastCheckedPosition = position
            }
        }
        holder.bind(item, isChecked, callback)
    }

    class ViewHolder private constructor(private val binding: ListItemTopicSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Topic, isChecked: Boolean, callback: () -> Unit) {
            binding.apply {
                topic = item
                radioButton.isChecked = isChecked
                radioButton.setOnClickListener {
                    callback.invoke()
                }
            }
        }

        fun uncheck() {
            binding.radioButton.isChecked = false
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTopicSelectBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TopicSelectDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.name == newItem.name
    }
}