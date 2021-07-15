package com.vanchel.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.vanchel.todolist.R
import com.vanchel.todolist.databinding.FragmentTopicBinding

class TopicFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTopicBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_topic, container, false)

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }
}