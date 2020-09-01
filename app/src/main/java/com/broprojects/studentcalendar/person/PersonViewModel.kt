package com.broprojects.studentcalendar.person

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.broprojects.studentcalendar.database.PeopleTableDao
import com.broprojects.studentcalendar.database.Person
import com.broprojects.studentcalendar.helpers.InputViewModel

class PersonViewModel(activity: Activity, dao: PeopleTableDao, private val personId: Long?) :
    InputViewModel<Person>(activity, dao) {

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person

    init {
        if (personId != null) {
            _person.value = getData(personId)
        }
    }

    fun saveData() {
        if (_person.value != null) {
            super.saveData(personId, _person.value!!)
        }
    }
}