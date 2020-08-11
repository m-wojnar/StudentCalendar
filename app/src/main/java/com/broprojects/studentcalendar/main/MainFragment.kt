package com.broprojects.studentcalendar.main

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentMainBinding
import com.google.android.material.navigation.NavigationView

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        val args = MainFragmentArgs.fromBundle(requireArguments())
        val viewModel = MainViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.welcomeText.text = getString(args.textResourceId)
        binding.welcomeButton.setBackgroundResource(viewModel.getSmallDrawableId(args.drawableResourceId))
        binding.floatingActionButton.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), args.colorResourceId)

        binding.welcomeButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWelcomeFragment())
        }

        binding.navigationDrawerButton.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.openDrawer(GravityCompat.START)
        }

        return binding.root
    }
}