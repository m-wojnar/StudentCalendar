package com.broprojects.studentcalendar.helpers

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.database.BaseDao
import kotlinx.coroutines.*

open class InputViewModel<T>(private val activity: Activity, private val dao: BaseDao<T>) : ViewModel() {
    private val _colorStateList = MutableLiveData<ColorStateList>()
    val colorStateList: LiveData<ColorStateList>
        get() = _colorStateList

    private val _goToMainFragment = MutableLiveData<Boolean>()
    val goToMainFragment: LiveData<Boolean>
        get() = _goToMainFragment

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        // Read chosen color from shared preferences
        val preferences = activity.getPreferences(Context.MODE_PRIVATE)
        val colorId = preferences.getInt(activity.getString(R.string.random_welcome_color), R.color.primary_color)
        _colorStateList.value = ContextCompat.getColorStateList(activity.applicationContext, colorId)!!
    }

    protected fun saveData(id: Long?, data: T) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                if (id == null) {
                    dao.insert(data)
                } else {
                    dao.update(data)
                }
            }
        }

        _goToMainFragment.value = true
    }

    fun getData(id: Long) = dao.get(id)

    fun goToMainFragmentDone() {
        _goToMainFragment.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    protected fun getString(id: Int) = activity.getString(id)
}