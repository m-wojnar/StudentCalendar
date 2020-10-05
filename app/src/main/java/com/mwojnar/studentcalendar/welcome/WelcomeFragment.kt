package com.mwojnar.studentcalendar.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.ToolbarActivity
import com.mwojnar.studentcalendar.databinding.FragmentWelcomeBinding
import com.mwojnar.studentcalendar.helpers.getIdentifier

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
        binding.lifecycleOwner = this

        viewModel.color.observe(viewLifecycleOwner, {
            val colorId = getIdentifier(requireContext(), it, R.string.type_color)
            binding.linearLayout.setBackgroundResource(colorId)
            toolbarActivity.setBackground(colorId)
        })

        viewModel.icon.observe(viewLifecycleOwner, {
            val iconId = getIdentifier(requireContext(), it, R.string.type_drawable)
            binding.welcomeImage.setImageResource(iconId)
        })

        viewModel.text.observe(viewLifecycleOwner, {
            val textId = getIdentifier(requireContext(), it, R.string.type_string)
            binding.welcomeText.text = getString(textId)
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