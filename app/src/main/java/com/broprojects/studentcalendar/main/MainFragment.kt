package com.broprojects.studentcalendar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentMainBinding
import com.broprojects.studentcalendar.welcome.WelcomeViewModelFactory
import com.google.android.material.navigation.NavigationView

class MainFragment : Fragment() {
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
        binding.lifecycleOwner = this

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
            binding.welcomeButton.setBackgroundResource(viewModel.getSmallDrawableId(it))
            headerView?.findViewById<ImageView>(R.id.header_welcome_image)?.setBackgroundResource(it)
        })

        // Set chosen text to the action bar
        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.welcomeText.text = getString(it)
        })

        // Go back to welcome fragment on welcomeButton click
        binding.welcomeButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWelcomeFragment())
        }

        // Open navigation drawer on navigationDrawerButton click
        binding.navigationDrawerButton.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.openDrawer(GravityCompat.START)
        }

        return binding.root
    }
}