package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.vanchel.todolist.R
import com.vanchel.todolist.adapters.MainAdapter
import com.vanchel.todolist.databinding.FragmentMainBinding
import com.vanchel.todolist.viewmodels.MainViewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(requireActivity().application)
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MainAdapter()
        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                MainFragmentDirections.actionMainFragmentToTaskFragment()
            )
        )

        viewModel.taskLists.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}