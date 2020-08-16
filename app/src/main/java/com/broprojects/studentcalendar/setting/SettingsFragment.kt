package com.broprojects.studentcalendar.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        binding.navigationBackButton.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToMainFragment())
        }

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, PreferencesFragment())
            .commit()

        return binding.root
    }

}