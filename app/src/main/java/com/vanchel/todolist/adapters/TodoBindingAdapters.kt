package com.vanchel.todolist.adapters

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.vanchel.todolist.R
import com.vanchel.todolist.domain.Task
import com.vanchel.todolist.domain.TaskList

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

@BindingAdapter("stripedOut")
fun setStripedText(textView: TextView, completed: Boolean) {
    textView.paintFlags = if (completed) {
        textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}