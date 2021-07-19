package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.vanchel.todolist.R
import com.vanchel.todolist.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        setHasOptionsMenu(true)

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)
    }
}