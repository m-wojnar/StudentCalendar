package com.broprojects.studentcalendar

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.broprojects.studentcalendar.helpers.createChannel

class StudentCalendarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Read system preference about selected theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val darkTheme = preferences.getBoolean(getString(R.string.theme_preference), true)

        // Set right theme
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Create notification channel
        createChannel(
            applicationContext,
            getString(R.string.reminders_channel_id),
            getString(R.string.reminders_channel_name),
            getString(R.string.reminders_channel_description)
        )
    }
}