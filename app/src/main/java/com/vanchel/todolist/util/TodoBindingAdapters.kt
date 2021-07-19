package com.vanchel.todolist.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.vanchel.todolist.R
import com.vanchel.todolist.domain.TaskList

class TodoBindingAdapters {
    @BindingAdapter("completionIndicatorsFormatted")
    fun setFormattedText(textView: TextView, taskList: TaskList) {
        val completedCount = taskList.tasks.count { it.completed }
        val uncompletedCount = taskList.tasks.size - completedCount

        textView.apply {
            text = resources.getString(
                R.string.completion_indicators_formatted,
                completedCount,
                uncompletedCount
            )
        }
    }
}