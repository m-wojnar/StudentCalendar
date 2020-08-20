package com.broprojects.studentcalendar.course

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R

data class IconDropdownItem(val name: String, val id: Int) {
    override fun toString() = name
}

class CourseViewModel(private val activity: Activity) : ViewModel() {
     val colorsItemsArray = arrayOf(
         IconDropdownItem(getString(R.string.orange), R.color.app_color_1),
         IconDropdownItem(getString(R.string.pink), R.color.app_color_2),
         IconDropdownItem(getString(R.string.purple), R.color.app_color_3),
         IconDropdownItem(getString(R.string.indigo), R.color.app_color_4),
         IconDropdownItem(getString(R.string.blue), R.color.app_color_5),
         IconDropdownItem(getString(R.string.sea), R.color.app_color_6),
         IconDropdownItem(getString(R.string.gray), R.color.app_color_7),
         IconDropdownItem(getString(R.string.green), R.color.app_color_8)
    )

    val iconsItemsArray = arrayOf(
        IconDropdownItem(getString(R.string.beach), R.drawable.ic_baseline_beach_access_24),
        IconDropdownItem(getString(R.string.tea), R.drawable.ic_baseline_emoji_food_beverage_24),
        IconDropdownItem(getString(R.string.cafe), R.drawable.ic_baseline_local_cafe_24),
        IconDropdownItem(getString(R.string.smile), R.drawable.ic_baseline_mood_24),
        IconDropdownItem(getString(R.string.music), R.drawable.ic_baseline_music_note_24),
        IconDropdownItem(getString(R.string.thumb_up), R.drawable.ic_baseline_thumb_up_24),
        IconDropdownItem(getString(R.string.sun), R.drawable.ic_baseline_wb_sunny_24)
    )

    private val _colorStateList = MutableLiveData<ColorStateList>()
    val colorStateList: LiveData<ColorStateList>
        get() = _colorStateList

    private val _goToMainFragment = MutableLiveData<Boolean>()
    val goToMainFragment: LiveData<Boolean>
        get() = _goToMainFragment

    init {
        // Read chosen color from shared preferences
        val preferences = activity.getPreferences(Context.MODE_PRIVATE)
        val colorId = preferences.getInt(getString(R.string.random_welcome_color), R.color.primary_color)
        _colorStateList.value = ContextCompat.getColorStateList(activity.applicationContext, colorId)!!
    }

    private fun getString(id: Int) = activity.getString(id)

    fun saveData() {
        // TODO add item to database
        _goToMainFragment.value = true
    }

    fun goToMainFragmentDone() {
        _goToMainFragment.value = false
    }
}