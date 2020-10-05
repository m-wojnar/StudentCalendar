package com.mwojnar.studentcalendar.helpers

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.BaseDao
import kotlinx.coroutines.*

open class InputViewModel<T>(
    private val activity: Activity,
    private val dao: BaseDao<T>,
    private val id: Long?,
    defaultModel: T
) : ViewModel() {

    private val _colorStateList = MutableLiveData<ColorStateList>()
    val colorStateList: LiveData<ColorStateList>
        get() = _colorStateList

    private val _goToMainFragment = MutableLiveData<Boolean>()
    val goToMainFragment: LiveData<Boolean>
        get() = _goToMainFragment

    protected val modelMutableLiveData = MutableLiveData(defaultModel)
    val model: LiveData<T>
        get() = modelMutableLiveData

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        // Read chosen color from shared preferences
        val preferences = activity.getPreferences(Context.MODE_PRIVATE)
        val colorName = preferences.getString(
            activity.getString(R.string.random_welcome_color_name),
            activity.resources.getResourceEntryName(R.color.app_color_10)
        )
        _colorStateList.value = ContextCompat.getColorStateList(
            activity.applicationContext,
            getIdentifier(activity.applicationContext, colorName, R.string.type_color)
        )!!

        // If id != null, then user is updating data
        if (id != null) {
            loadData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun saveData(funcOnFinish: (id: Long) -> Unit = {}) {
        if (id == null) {
            dbOperation {
                val modelId = dao.insert(modelMutableLiveData.value!!)
                funcOnFinish(modelId)
            }
        } else {
            dbOperation {
                dao.update(modelMutableLiveData.value!!)
                funcOnFinish(id)
            }
        }

        _goToMainFragment.value = true
    }

    private fun loadData() {
        dbOperation { modelMutableLiveData.postValue(dao.get(id!!)) }
    }

    open fun deleteData() {
        dbOperation { dao.delete(modelMutableLiveData.value!!) }
        _goToMainFragment.value = true
    }

    protected fun dbOperation(func: () -> Unit) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                func()
            }
        }
    }

    fun setBoxStrokeColorForChildren(layout: ViewGroup, color: ColorStateList) {
        for (child in layout.children) {
            if (child is TextInputLayout) {
                child.setBoxStrokeColorStateList(color)
            } else if (child is ViewGroup) {
                setBoxStrokeColorForChildren(child, color)
            }
        }
    }

    fun goToMainFragmentDone() {
        _goToMainFragment.value = false
    }

    protected fun getString(id: Int) =
        activity.getString(id)
}