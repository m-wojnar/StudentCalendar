package com.broprojects.studentcalendar.helpers

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.room.TypeConverter
import com.broprojects.studentcalendar.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*

fun validateEmpty(fragment: Fragment, inputLayout: TextInputLayout, editText: TextView) =
    if (editText.text.isNullOrEmpty()) {
        inputLayout.error = fragment.getString(R.string.required)
        false
    } else {
        inputLayout.error = null
        true
    }

fun FragmentActivity.dateTimePickerDialog(func: (date: Date) -> Unit) {
    val currentDateTime = Calendar.getInstance()

    DatePickerDialog(this, { _, year, month, day ->
        TimePickerDialog(this, { _, hour, min ->
            val dateTime =  Calendar.getInstance()
            dateTime.set(year, month, day, hour, min)
            func(dateTime.time)
        }, currentDateTime[Calendar.HOUR], currentDateTime[Calendar.MINUTE], false).show()
    }, currentDateTime[Calendar.YEAR], currentDateTime[Calendar.MONTH], currentDateTime[Calendar.DATE]).show()
}

fun FragmentActivity.datePickerDialog(func: (date: Date) -> Unit) {
    val currentDateTime = Calendar.getInstance()

    DatePickerDialog(this, { _, year, month, day ->
        val dateTime =  Calendar.getInstance()
        dateTime.set(year, month, day)
        func(dateTime.time)
    }, currentDateTime[Calendar.YEAR], currentDateTime[Calendar.MONTH], currentDateTime[Calendar.DATE]).show()
}

fun FragmentActivity.timePickerDialog(func: (date: Date) -> Unit) {
    val currentDateTime = Calendar.getInstance()

    TimePickerDialog(this, { _, hour, min ->
        val dateTime =  Calendar.getInstance()
        dateTime.set(currentDateTime[Calendar.YEAR], currentDateTime[Calendar.MONTH], currentDateTime[Calendar.DATE], hour, min)
        func(dateTime.time)
    }, currentDateTime[Calendar.HOUR], currentDateTime[Calendar.MINUTE], false).show()
}

fun Date.toDateString(context: Context): String {
    val dateFormat = DateFormat.getDateFormat(context)
    return dateFormat.format(this)
}

fun Date.toTimeString(context: Context): String {
    val timeFormat = DateFormat.getTimeFormat(context)
    return timeFormat.format(this)
}

fun Date.toDateTimeString(context: Context): String {
    val dateFormat = DateFormat.getDateFormat(context)
    val timeFormat = DateFormat.getTimeFormat(context)
    return context.getString(R.string.date_time, dateFormat.format(this), timeFormat.format(this))
}

class Converters {
    @TypeConverter
    fun dateToLong(date: Date?) = date?.time

    @TypeConverter
    fun longToDate(long: Long?) = long?.let { Date(it) }
}