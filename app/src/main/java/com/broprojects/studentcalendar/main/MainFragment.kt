package com.broprojects.studentcalendar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.databinding.FragmentMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private val animationDuration = 300L
    private val translationXValue = -60f

    private lateinit var binding: FragmentMainBinding
    private val toolbarActivity: ToolbarActivity
        get() = activity as ToolbarActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val viewModelFactory = MainViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Get header of navigation drawer
        val navigationView = activity?.findViewById<NavigationView>(R.id.navigation_view)
        val headerView = navigationView?.getHeaderView(0)

        // Set chosen color to the action bar and navigation drawer
        viewModel.color.observe(viewLifecycleOwner, {
            binding.floatingActionButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), it)
            headerView?.setBackgroundResource(it)
        })

        // Set chosen text to the action bar
        viewModel.text.observe(viewLifecycleOwner, {
            toolbarActivity.setActionBarText(it)
        })

        binding.tabLayout.addOnTabSelectedListener(this)

        binding.floatingActionButton.setOnClickListener {
            when (binding.tabLayout.selectedTabPosition) {
                0 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTaskFragment())
                1 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTaskFragment())
                2 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToTestFragment())
                3 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToScheduleFragment())
                4 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToCourseFragment())
                5 -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToPersonFragment())
                else -> return@setOnClickListener
            }
        }

        return binding.root
    }

    // Show action bar icon only on this fragment
    override fun onStart() {
        super.onStart()
        toolbarActivity.showActionBarIcon()
    }

    override fun onStop() {
        super.onStop()
        toolbarActivity.hideActionBarIcon()
    }

    // Reload recycler view with animation on tab change
    override fun onTabSelected(tab: TabLayout.Tab?) {
        hideRecyclerView {
            loadDataToRecyclerView()
            showRecyclerView()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}

    private fun hideRecyclerView(func: () -> Unit) {
        binding.recyclerView.animate()
            .alpha(0.0f)
            .translationX(translationXValue)
            .setDuration(animationDuration)
            .withEndAction(func)
            .start()
    }

    private fun showRecyclerView() {
        binding.recyclerView.animate()
            .alpha(1.0f)
            .translationX(0f)
            .setDuration(animationDuration)
            .start()
    }

    private fun loadDataToRecyclerView() {
        // TODO load data to recycler view
    }
}