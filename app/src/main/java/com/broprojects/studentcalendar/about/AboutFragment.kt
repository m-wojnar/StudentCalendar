package com.broprojects.studentcalendar.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAboutBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_about, container,false
        )

        binding.navigationBackButton.setOnClickListener {
            findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToMainFragment())
        }

        return binding.root
    }
}