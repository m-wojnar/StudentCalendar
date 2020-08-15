package com.broprojects.studentcalendar.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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

        val viewModel = WelcomeViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        var color = R.color.app_color_1
        viewModel.color.observe(viewLifecycleOwner, Observer {
            color = it
            binding.linearLayout.setBackgroundResource(it)
        })

        var drawable = R.drawable.ic_baseline_beach_access_140
        viewModel.drawable.observe(viewLifecycleOwner, Observer {
            drawable = it
            binding.welcomeImage.setImageResource(it)
        })

        var text = R.string.welcome_1
        viewModel.text.observe(viewLifecycleOwner, Observer {
            text = it
            binding.welcomeText.text = getString(it)
        })

        viewModel.mainFragmentEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment(text, color, drawable)
                )
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }
}