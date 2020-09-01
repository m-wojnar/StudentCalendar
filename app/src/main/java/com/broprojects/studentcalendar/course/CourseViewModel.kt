package com.broprojects.studentcalendar.course

import android.app.Activity
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.database.Course
import com.broprojects.studentcalendar.database.CoursesTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel

data class IconDropdownItem(val name: String, val id: Int) {
    override fun toString() = name
}

class CourseViewModel(activity: Activity, private val dao: CoursesTableDao) : InputViewModel(activity) {
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

    fun saveData(data: Course) {
        super.saveData(data.courseId, data, dao)
    }
}