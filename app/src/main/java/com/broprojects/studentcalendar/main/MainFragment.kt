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
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.databinding.FragmentMainBinding
import com.google.android.material.navigation.NavigationView

class MainFragment : Fragment() {
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

        // Show custom toolbar
        toolbarActivity.showActionBarAnimation()
        toolbarActivity.setBackground(R.color.transparent)
        toolbarActivity.showActionBarIcon()

        return binding.root
    }
}