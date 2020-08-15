package com.broprojects.studentcalendar.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false
        )

        val viewModelFactory = WelcomeViewModelFactory(requireActivity())
        val viewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.color.observe(viewLifecycleOwner, Observer {
            binding.linearLayout.setBackgroundResource(it)
        })

        viewModel.icon.observe(viewLifecycleOwner, Observer {
            binding.welcomeImage.setImageResource(it)
        })

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.welcomeText.text = getString(it)
        })

        viewModel.mainFragmentEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }
}