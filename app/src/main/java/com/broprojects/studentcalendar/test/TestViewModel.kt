package com.broprojects.studentcalendar.test

import android.app.Activity
import androidx.lifecycle.Transformations
import com.broprojects.studentcalendar.database.Test
import com.broprojects.studentcalendar.database.TestsTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel
import com.broprojects.studentcalendar.helpers.toDateTimeString
import java.util.*

class TestViewModel(activity: Activity, dao: TestsTableDao, testId: Long?) :
    InputViewModel<Test>(activity, dao, testId, Test()) {

    val whenDateTime = Transformations.map(modelMutableLiveData) {
        modelMutableLiveData.value?.whenDateTime?.toDateTimeString(activity.applicationContext)
    }

    fun setWhenDateTime(whenDateTime: Date) {
        modelMutableLiveData.value?.whenDateTime = whenDateTime
    }
}