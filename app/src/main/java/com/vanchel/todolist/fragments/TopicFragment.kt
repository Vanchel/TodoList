package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vanchel.todolist.R
import com.vanchel.todolist.adapters.TopicAdapter
import com.vanchel.todolist.databinding.FragmentTopicBinding
import com.vanchel.todolist.domain.Topic
import com.vanchel.todolist.viewmodels.TopicViewModel

class TopicFragment : BottomSheetDialogFragment() {
    private val viewModel: TopicViewModel by viewModels {
        val application = requireActivity().application
        TopicViewModel.Factory(application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTopicBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_topic, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = TopicAdapter(this::onDeleteTopic)
        binding.recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            onAddNewTopic(binding.newTopicNameEdit)
        }

        viewModel.topics.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        return binding.root
    }

    private fun onAddNewTopic(editText: EditText) {
        val name = editText.text.trim().toString()

        // No need to call isBlank() because of trim called earlier
        if (name.isEmpty()) return

        viewModel.addTopic(name)
        findNavController().popBackStack()
    }

    private fun onDeleteTopic(topic: Topic) {
        viewModel.deleteTopic(topic)
        findNavController().popBackStack()
    }
}