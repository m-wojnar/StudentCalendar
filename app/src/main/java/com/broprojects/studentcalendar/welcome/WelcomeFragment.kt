package com.broprojects.studentcalendar.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.ToolbarActivity
import com.broprojects.studentcalendar.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var viewModel: WelcomeViewModel
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
        viewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.color.observe(viewLifecycleOwner, {
            binding.linearLayout.setBackgroundResource(it)
            toolbarActivity.setBackground(it)
        })

        viewModel.icon.observe(viewLifecycleOwner, {
            binding.welcomeImage.setImageResource(it)
        })

        viewModel.text.observe(viewLifecycleOwner, {
            binding.welcomeText.text = getString(it)
        })

        viewModel.mainFragmentEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment())
                viewModel.goToMainFragmentDone()
            }
        })

        return binding.root
    }

    // Hide action bar only in this fragment
    override fun onStart() {
        super.onStart()

        if (viewModel.hideActionBarWithAnimation()) {
            toolbarActivity.hideActionBarAnimation()
        } else {
            toolbarActivity.hideActionBar()
        }

        viewModel.hideActionBarDone()
    }

    override fun onStop() {
        super.onStop()

        toolbarActivity.setBackground(R.color.transparent)
        toolbarActivity.showActionBarAnimation()
    }
}