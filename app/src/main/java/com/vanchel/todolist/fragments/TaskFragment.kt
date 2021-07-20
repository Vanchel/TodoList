package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.vanchel.todolist.R
import com.vanchel.todolist.adapters.TopicSelectAdapter
import com.vanchel.todolist.databinding.FragmentTaskBinding
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.viewmodels.TaskViewModel

class TaskFragment : Fragment() {
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModel.Factory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = TopicSelectAdapter(viewModel::selectTopic)
        binding.recyclerView.adapter = adapter

        viewModel.topics.observe(viewLifecycleOwner, adapter::submitList)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)
    }
}