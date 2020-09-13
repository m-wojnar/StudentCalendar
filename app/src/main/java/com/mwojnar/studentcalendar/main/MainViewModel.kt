package com.mwojnar.studentcalendar.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.*
import kotlinx.coroutines.*

class MainViewModel(private val activity: Activity) : ViewModel() {
    private val _text = MutableLiveData<Int>()
    val text: LiveData<Int>
        get() = _text

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int>
        get() = _color

    private val _coursesData = MutableLiveData<List<Course>>()
    val coursesData: LiveData<List<Course>>
        get() = _coursesData

    private val _peopleData = MutableLiveData<List<Person>>()
    val peopleData: LiveData<List<Person>>
        get() = _peopleData

    private val _schedulesData = MutableLiveData<List<ScheduleAndCourseAndPerson>>()
    val schedulesData: LiveData<List<ScheduleAndCourseAndPerson>>
        get() = _schedulesData

    private val _testsData = MutableLiveData<List<TestAndCourse>>()
    val testsData: LiveData<List<TestAndCourse>>
        get() = _testsData

    private val _tasksData = MutableLiveData<List<TaskAndCourse>>()
    val tasksData: LiveData<List<TaskAndCourse>>
        get() = _tasksData

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        // Get chosen color, text and icon from shared preferences
        val sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        _color.value = sharedPreferences.getInt(
            activity.getString(R.string.random_welcome_color),
            R.color.app_color_4
        )
        _text.value = sharedPreferences.getInt(
            activity.getString(R.string.random_welcome_text),
            R.string.welcome_6
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun loadData(selectedTab: Int) {
        val database = CalendarDatabase.getInstance(activity.applicationContext)

        when (selectedTab) {
            1 -> dbOperation { _tasksData.postValue(database.tasksTableDao.getAllWithCourse()) }
            2 -> dbOperation { _testsData.postValue(database.testsTableDao.getAllWithCourse()) }
            3 -> dbOperation { _schedulesData.postValue(database.schedulesTableDao.getAllWithCourseAndPerson()) }
            4 -> dbOperation { _coursesData.postValue(database.coursesTableDao.getAll()) }
            5 -> dbOperation { _peopleData.postValue(database.peopleTableDao.getAll()) }
            else -> {}
        }
    }

    private fun dbOperation(func: () -> Unit) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                func()
            }
        }
    }
}