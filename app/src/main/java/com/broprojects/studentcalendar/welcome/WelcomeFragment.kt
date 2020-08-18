package com.broprojects.studentcalendar.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private val toolbarActivity: ToolbarActivity
        get() = activity as ToolbarActivity

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

        viewModel.color.observe(viewLifecycleOwner, Observer {
            binding.linearLayout.setBackgroundResource(it)
            toolbarActivity.setBackground(it)
        })

        viewModel.icon.observe(viewLifecycleOwner, Observer {
            binding.welcomeImage.setImageResource(it)
        })

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.welcomeText.text = getString(it)
        })

        viewModel.mainFragmentEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        // Hide custom toolbar
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(getString(R.string.show_welcome), true)
            && viewModel.firstWelcome.value == true) {
            toolbarActivity.hideActionBar()
        } else {
            toolbarActivity.hideActionBarAnimation()
        }
        viewModel.firstWelcomeDone()

        return binding.root
    }
}