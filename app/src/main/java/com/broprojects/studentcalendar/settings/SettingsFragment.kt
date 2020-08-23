package com.broprojects.studentcalendar.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.broprojects.studentcalendar.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}