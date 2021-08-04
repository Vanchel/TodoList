package com.vanchel.todolist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.vanchel.todolist.R
import com.vanchel.todolist.adapters.MainAdapter
import com.vanchel.todolist.databinding.FragmentMainBinding
import com.vanchel.todolist.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = MainAdapter {
            findNavController().navigate(
                /* The choice in favor of two different arguments was made for two reasons.
                * First, transferring large data objects between fragments is an anti-pattern
                * (although in this particular case the object is quite small and is represented
                * only by the same two transmitted fields).
                * Secondly, and this is the main reason - in order to transmit a topic directly,
                * it had to implement parcelable interface, and in this case the model ceases to be
                * platform-independent (there is also an option with implementing serializable
                * interface, but in this example it also seemed to me not the most convenient
                * solution). */
                MainFragmentDirections.actionMainFragmentToTaskListFragment(
                    it.topic.id,
                    it.topic.name
                )
            )
        }
        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                MainFragmentDirections.actionMainFragmentToTaskFragment()
            )
        )

        viewModel.taskLists.observe(viewLifecycleOwner, adapter::submitList)

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