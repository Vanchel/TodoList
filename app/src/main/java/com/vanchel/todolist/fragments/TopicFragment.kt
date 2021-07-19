package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vanchel.todolist.R
import com.vanchel.todolist.databinding.FragmentTopicBinding
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

        return binding.root
    }
}