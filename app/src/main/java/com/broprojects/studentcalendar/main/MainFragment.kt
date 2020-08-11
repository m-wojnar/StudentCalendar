package com.broprojects.studentcalendar.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentMainBinding

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
        binding.welcomeImage.setBackgroundResource(viewModel.getSmallDrawableId(args.drawableResourceId))
        binding.floatingActionButton.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), args.colorResourceId)

        viewModel.welcomeFragmentEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToWelcomeFragment())
                viewModel.goToWelcomeFragmentDone()
            }
        })

        return binding.root
    }
}