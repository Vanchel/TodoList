package com.vanchel.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vanchel.todolist.adapters.TaskListAdapter
import com.vanchel.todolist.databinding.FragmentTaskListBinding
import com.vanchel.todolist.viewmodels.TaskListViewModel

class TaskListFragment : Fragment() {
    private val args: TaskListFragmentArgs by navArgs()
    private val viewModel: TaskListViewModel by viewModels {
        TaskListViewModel.Factory(requireActivity().application, args.topicId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTaskListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = TaskListAdapter {
            viewModel.completeTask(it)
        }
        binding.recyclerView.adapter = adapter

        viewModel.taskList.observe(viewLifecycleOwner) {
            adapter.submitList(it.tasks)
        }

        return binding.root
    }
}