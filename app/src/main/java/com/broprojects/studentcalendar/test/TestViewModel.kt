package com.broprojects.studentcalendar.test

import android.app.Activity
import com.broprojects.studentcalendar.database.Test
import com.broprojects.studentcalendar.database.TestsTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel

class TestViewModel(activity: Activity, private val dao: TestsTableDao) : InputViewModel(activity) {
    fun saveData(data: Test) {
        super.saveData(data.testId, data, dao)
    }
}