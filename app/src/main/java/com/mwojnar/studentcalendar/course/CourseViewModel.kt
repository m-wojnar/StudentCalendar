package com.mwojnar.studentcalendar.course

import android.app.Activity
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.Course
import com.mwojnar.studentcalendar.database.CoursesTableDao
import com.mwojnar.studentcalendar.helpers.IconDropdownItem
import com.mwojnar.studentcalendar.helpers.InputViewModel

class CourseViewModel(activity: Activity, dao: CoursesTableDao, courseId: Long?) :
    InputViewModel<Course>(activity, dao, courseId, Course()) {
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

    val colorsTextMap = mapOf(
        Pair(R.color.app_color_1, getString(R.string.orange)),
        Pair(R.color.app_color_2, getString(R.string.pink)),
        Pair(R.color.app_color_3, getString(R.string.purple)),
        Pair(R.color.app_color_4, getString(R.string.indigo)),
        Pair(R.color.app_color_5, getString(R.string.blue)),
        Pair(R.color.app_color_6, getString(R.string.sea)),
        Pair(R.color.app_color_7, getString(R.string.gray)),
        Pair(R.color.app_color_8, getString(R.string.green))
    )

    val iconsItemsArray = arrayOf(
        IconDropdownItem(getString(R.string.beach), R.drawable.ic_baseline_beach_access_24),
        IconDropdownItem(getString(R.string.tea), R.drawable.ic_baseline_emoji_food_beverage_24),
        IconDropdownItem(getString(R.string.cafe), R.drawable.ic_baseline_local_cafe_24),
        IconDropdownItem(getString(R.string.smile), R.drawable.ic_baseline_mood_24),
        IconDropdownItem(getString(R.string.music), R.drawable.ic_baseline_music_note_24),
        IconDropdownItem(getString(R.string.thumb_up), R.drawable.ic_baseline_thumb_up_24),
        IconDropdownItem(getString(R.string.sun), R.drawable.ic_baseline_wb_sunny_24),
        IconDropdownItem(getString(R.string.airplane), R.drawable.ic_baseline_airplane_active_24),
        IconDropdownItem(getString(R.string.android), R.drawable.ic_baseline_android_24),
        IconDropdownItem(getString(R.string.cake), R.drawable.ic_baseline_cake_24),
        IconDropdownItem(getString(R.string.camera), R.drawable.ic_baseline_camera_24),
        IconDropdownItem(getString(R.string.message), R.drawable.ic_baseline_chat_24),
        IconDropdownItem(getString(R.string.laptop), R.drawable.ic_baseline_laptop_24),
        IconDropdownItem(getString(R.string.leaf), R.drawable.ic_baseline_leaf_24),
        IconDropdownItem(getString(R.string.notes), R.drawable.ic_baseline_notes_24)
    )

    val iconsTextMap = mapOf(
        Pair(R.drawable.ic_baseline_beach_access_24, getString(R.string.beach)),
        Pair(R.drawable.ic_baseline_emoji_food_beverage_24, getString(R.string.tea)),
        Pair(R.drawable.ic_baseline_local_cafe_24, getString(R.string.cafe)),
        Pair(R.drawable.ic_baseline_mood_24, getString(R.string.smile)),
        Pair(R.drawable.ic_baseline_music_note_24, getString(R.string.music)),
        Pair(R.drawable.ic_baseline_thumb_up_24, getString(R.string.thumb_up)),
        Pair(R.drawable.ic_baseline_wb_sunny_24, getString(R.string.sun)),
        Pair(R.drawable.ic_baseline_airplane_active_24, getString(R.string.airplane)),
        Pair(R.drawable.ic_baseline_android_24, getString(R.string.android)),
        Pair(R.drawable.ic_baseline_cake_24, getString(R.string.cake)),
        Pair(R.drawable.ic_baseline_camera_24, getString(R.string.camera)),
        Pair(R.drawable.ic_baseline_chat_24, getString(R.string.message)),
        Pair(R.drawable.ic_baseline_laptop_24, getString(R.string.laptop)),
        Pair(R.drawable.ic_baseline_leaf_24, getString(R.string.leaf)),
        Pair(R.drawable.ic_baseline_notes_24, getString(R.string.notes)),
    )

    fun setColor(colorId: Int) {
        modelMutableLiveData.value?.colorId = colorId
    }

    fun setIcon(iconId: Int) {
        modelMutableLiveData.value?.iconId = iconId
    }
}