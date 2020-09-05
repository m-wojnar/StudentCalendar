package com.broprojects.studentcalendar.person

import android.app.Activity
import com.broprojects.studentcalendar.database.PeopleTableDao
import com.broprojects.studentcalendar.database.Person
import com.broprojects.studentcalendar.helpers.InputViewModel

class PersonViewModel(activity: Activity, dao: PeopleTableDao, personId: Long?) :
    InputViewModel<Person>(activity, dao, personId, Person())