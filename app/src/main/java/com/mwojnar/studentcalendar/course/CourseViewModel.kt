package com.mwojnar.studentcalendar.course

import android.app.Activity
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.Course
import com.mwojnar.studentcalendar.database.CoursesTableDao
import com.mwojnar.studentcalendar.helpers.IconDropdownItem
import com.mwojnar.studentcalendar.helpers.InputViewModel

class CourseViewModel(private val activity: Activity, dao: CoursesTableDao, courseId: Long?) :
    InputViewModel<Course>(activity, dao, courseId, Course()) {
    val colorsItemsArray = arrayOf(
        IconDropdownItem(getString(R.string.brown), getResourceName(R.color.app_color_9)),
        IconDropdownItem(getString(R.string.red), getResourceName(R.color.app_color_1)),
        IconDropdownItem(getString(R.string.orange), getResourceName(R.color.app_color_12)),
        IconDropdownItem(getString(R.string.yellow), getResourceName(R.color.app_color_10)),
        IconDropdownItem(getString(R.string.green), getResourceName(R.color.app_color_8)),
        IconDropdownItem(getString(R.string.sea), getResourceName(R.color.app_color_6)),
        IconDropdownItem(getString(R.string.cyan), getResourceName(R.color.app_color_11)),
        IconDropdownItem(getString(R.string.blue), getResourceName(R.color.app_color_5)),
        IconDropdownItem(getString(R.string.indigo), getResourceName(R.color.app_color_4)),
        IconDropdownItem(getString(R.string.purple), getResourceName(R.color.app_color_3)),
        IconDropdownItem(getString(R.string.pink), getResourceName(R.color.app_color_2)),
        IconDropdownItem(getString(R.string.maroon), getResourceName(R.color.app_color_13)),
        IconDropdownItem(getString(R.string.steel), getResourceName(R.color.app_color_14)),
        IconDropdownItem(getString(R.string.gray), getResourceName(R.color.app_color_7))
    )

    val colorsTextMap = mapOf(
        Pair(getResourceName(R.color.app_color_9), getString(R.string.brown)),
        Pair(getResourceName(R.color.app_color_1), getString(R.string.red)),
        Pair(getResourceName(R.color.app_color_12), getString(R.string.orange)),
        Pair(getResourceName(R.color.app_color_10), getString(R.string.yellow)),
        Pair(getResourceName(R.color.app_color_8), getString(R.string.green)),
        Pair(getResourceName(R.color.app_color_6), getString(R.string.sea)),
        Pair(getResourceName(R.color.app_color_11), getString(R.string.cyan)),
        Pair(getResourceName(R.color.app_color_5), getString(R.string.blue)),
        Pair(getResourceName(R.color.app_color_4), getString(R.string.indigo)),
        Pair(getResourceName(R.color.app_color_3), getString(R.string.purple)),
        Pair(getResourceName(R.color.app_color_2), getString(R.string.pink)),
        Pair(getResourceName(R.color.app_color_13), getString(R.string.maroon)),
        Pair(getResourceName(R.color.app_color_14), getString(R.string.steel)),
        Pair(getResourceName(R.color.app_color_7), getString(R.string.gray))
    )

    val iconsItemsArray = arrayOf(
        IconDropdownItem(getString(R.string.beach), getResourceName(R.drawable.ic_baseline_beach_access_24)),
        IconDropdownItem(getString(R.string.tea), getResourceName(R.drawable.ic_baseline_emoji_food_beverage_24)),
        IconDropdownItem(getString(R.string.cafe), getResourceName(R.drawable.ic_baseline_local_cafe_24)),
        IconDropdownItem(getString(R.string.smile), getResourceName(R.drawable.ic_baseline_mood_24)),
        IconDropdownItem(getString(R.string.music), getResourceName(R.drawable.ic_baseline_music_note_24)),
        IconDropdownItem(getString(R.string.thumb_up), getResourceName(R.drawable.ic_baseline_thumb_up_24)),
        IconDropdownItem(getString(R.string.sun), getResourceName(R.drawable.ic_baseline_wb_sunny_24)),
        IconDropdownItem(getString(R.string.airplane), getResourceName(R.drawable.ic_baseline_airplane_active_24)),
        IconDropdownItem(getString(R.string.android), getResourceName(R.drawable.ic_baseline_android_24)),
        IconDropdownItem(getString(R.string.cake), getResourceName(R.drawable.ic_baseline_cake_24)),
        IconDropdownItem(getString(R.string.camera), getResourceName(R.drawable.ic_baseline_camera_24)),
        IconDropdownItem(getString(R.string.message), getResourceName(R.drawable.ic_baseline_chat_24)),
        IconDropdownItem(getString(R.string.laptop), getResourceName(R.drawable.ic_baseline_laptop_24)),
        IconDropdownItem(getString(R.string.leaf), getResourceName(R.drawable.ic_baseline_leaf_24)),
        IconDropdownItem(getString(R.string.notes), getResourceName(R.drawable.ic_baseline_notes_24))
    )

    val iconsTextMap = mapOf(
        Pair(getResourceName(R.drawable.ic_baseline_beach_access_24), getString(R.string.beach)),
        Pair(getResourceName(R.drawable.ic_baseline_emoji_food_beverage_24), getString(R.string.tea)),
        Pair(getResourceName(R.drawable.ic_baseline_local_cafe_24), getString(R.string.cafe)),
        Pair(getResourceName(R.drawable.ic_baseline_mood_24), getString(R.string.smile)),
        Pair(getResourceName(R.drawable.ic_baseline_music_note_24), getString(R.string.music)),
        Pair(getResourceName(R.drawable.ic_baseline_thumb_up_24), getString(R.string.thumb_up)),
        Pair(getResourceName(R.drawable.ic_baseline_wb_sunny_24), getString(R.string.sun)),
        Pair(getResourceName(R.drawable.ic_baseline_airplane_active_24), getString(R.string.airplane)),
        Pair(getResourceName(R.drawable.ic_baseline_android_24), getString(R.string.android)),
        Pair(getResourceName(R.drawable.ic_baseline_cake_24), getString(R.string.cake)),
        Pair(getResourceName(R.drawable.ic_baseline_camera_24), getString(R.string.camera)),
        Pair(getResourceName(R.drawable.ic_baseline_chat_24), getString(R.string.message)),
        Pair(getResourceName(R.drawable.ic_baseline_laptop_24), getString(R.string.laptop)),
        Pair(getResourceName(R.drawable.ic_baseline_leaf_24), getString(R.string.leaf)),
        Pair(getResourceName(R.drawable.ic_baseline_notes_24), getString(R.string.notes)),
    )

    fun setColor(colorName: String) {
        modelMutableLiveData.value?.colorName = colorName
    }

    fun setIcon(iconName: String) {
        modelMutableLiveData.value?.iconName = iconName
    }

    private fun getResourceName(id: Int) =
        activity.resources.getResourceEntryName(id)
}