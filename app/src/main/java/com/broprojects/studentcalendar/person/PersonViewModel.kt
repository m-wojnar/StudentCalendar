package com.broprojects.studentcalendar.person

import android.app.Activity
import com.broprojects.studentcalendar.database.PeopleTableDao
import com.broprojects.studentcalendar.database.Person
import com.broprojects.studentcalendar.helpers.InputViewModel

class PersonViewModel(activity: Activity, private val dao: PeopleTableDao) : InputViewModel(activity) {
    fun saveData(data: Person) {
        super.saveData(data.personId, data, dao)
    }
}