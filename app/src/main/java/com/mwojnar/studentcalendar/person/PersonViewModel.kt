package com.mwojnar.studentcalendar.person

import android.app.Activity
import com.mwojnar.studentcalendar.database.PeopleTableDao
import com.mwojnar.studentcalendar.database.Person
import com.mwojnar.studentcalendar.helpers.InputViewModel

class PersonViewModel(activity: Activity, dao: PeopleTableDao, personId: Long?) :
    InputViewModel<Person>(activity, dao, personId, Person())