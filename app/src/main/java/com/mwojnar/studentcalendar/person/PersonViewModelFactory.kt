package com.mwojnar.studentcalendar.person

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwojnar.studentcalendar.database.PeopleTableDao

class PersonViewModelFactory(
    private val activity: Activity,
    private val dao: PeopleTableDao,
    private val personId: Long?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonViewModel(activity, dao, personId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}