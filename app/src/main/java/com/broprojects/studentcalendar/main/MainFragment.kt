package com.broprojects.studentcalendar.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
        binding.welcomeImage.setImageResource(viewModel.getSmallDrawableId(args.drawableResourceId))
        binding.gradientBackground.setBackgroundResource(args.colorResourceId)

        return binding.root
    }
}