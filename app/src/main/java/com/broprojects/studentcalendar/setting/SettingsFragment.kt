package com.broprojects.studentcalendar.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.databinding.FragmentSettingsBinding

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, PreferencesFragment())
            .commit()

        return binding.root
    }

}