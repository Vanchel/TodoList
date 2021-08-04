package com.vanchel.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.vanchel.todolist.adapters.TaskListAdapter
import com.vanchel.todolist.databinding.FragmentTaskListBinding
import com.vanchel.todolist.util.ItemRemoveCallback
import com.vanchel.todolist.viewmodels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment() {
    private val args: TaskListFragmentArgs by navArgs()

    @Inject lateinit var taskListViewModelFactory: TaskListViewModel.Factory
    private val viewModel: TaskListViewModel by viewModels {
        TaskListViewModel.provideFactory(taskListViewModelFactory, args.topicId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTaskListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = TaskListAdapter(viewModel::updateTask)
        binding.recyclerView.adapter = adapter

        val itemRemoveCallback = ItemRemoveCallback {
            val item = adapter.currentList.elementAt(it)
            viewModel.deleteTask(item)
        }
        val itemTouchHelper = ItemTouchHelper(itemRemoveCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        viewModel.taskList.observe(viewLifecycleOwner) {
            adapter.submitList(it.tasks)
        }

        return binding.root
    }
}