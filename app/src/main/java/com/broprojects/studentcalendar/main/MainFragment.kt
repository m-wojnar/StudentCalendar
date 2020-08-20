package com.broprojects.studentcalendar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.databinding.FragmentMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private val toolbarActivity: ToolbarActivity
        get() = activity as ToolbarActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        val viewModelFactory = MainViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel

        // Get header of navigation drawer
        val navigationView = activity?.findViewById<NavigationView>(R.id.navigation_view)
        val headerView = navigationView?.getHeaderView(0)

        // Set chosen color to the action bar and navigation drawer
        viewModel.color.observe(viewLifecycleOwner, Observer {
            binding.floatingActionButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), it)
            headerView?.setBackgroundResource(it)
        })

        // Set chosen icon to the action bar and navigation drawer
        viewModel.icon.observe(viewLifecycleOwner, Observer {
            toolbarActivity.setActionBarIcon(viewModel.getSmallDrawableId(it))
            headerView?.findViewById<ImageView>(R.id.header_welcome_image)?.setBackgroundResource(it)
        })

        // Set chosen text to the action bar
        viewModel.text.observe(viewLifecycleOwner, Observer {
            toolbarActivity.setActionBarText(it)
        })

        binding.tabLayout.addOnTabSelectedListener(this)

        binding.floatingActionButton.setOnClickListener {
            when (binding.tabLayout.selectedTabPosition) {
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

    override fun onTabSelected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}