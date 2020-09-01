package com.broprojects.studentcalendar.test

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.broprojects.studentcalendar.database.Test
import com.broprojects.studentcalendar.database.TestsTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel
import com.broprojects.studentcalendar.helpers.toDateTimeString
import java.util.*

class TestViewModel(activity: Activity, dao: TestsTableDao, private val testId: Long?) :
    InputViewModel<Test>(activity, dao) {

    private val _test = MutableLiveData<Test>()
    val test: LiveData<Test>
        get() = _test

    val whenDateTime = Transformations.map(test) {
        test.value?.whenDateTime?.toDateTimeString(activity.applicationContext)
    }

    init {
        if (testId != null) {
            _test.value = getData(testId)
        }
    }

    fun setWhenDateTime(whenDateTime: Date) {
        _test.value?.whenDateTime = whenDateTime
    }

    fun saveData() {
        if (_test.value != null) {
            super.saveData(testId, _test.value!!)
        }
    }
}