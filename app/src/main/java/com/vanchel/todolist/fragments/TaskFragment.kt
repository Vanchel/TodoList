package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanchel.todolist.R
import com.vanchel.todolist.adapters.TopicSelectAdapter
import com.vanchel.todolist.databinding.FragmentTaskBinding
import com.vanchel.todolist.viewmodels.TaskViewModel

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModel.Factory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = TopicSelectAdapter {
            viewModel.selectTopic(it)
            requireActivity().invalidateOptionsMenu()
        }
        binding.recyclerView.adapter = adapter

        viewModel.topics.observe(viewLifecycleOwner, adapter::submitList)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.confirm_add_task -> {
                onAddTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.confirm_add_task)
        item.isEnabled = viewModel.isTopicSelected
    }

    private fun onAddTask() {
        val title = binding.newTaskNameEdit.text.trim().toString()

        // No need to call isBlank() because of trim called earlier
        if (title.isEmpty()) return

        viewModel.addTask(title)
        findNavController().popBackStack()
    }
}