package com.broprojects.studentcalendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.FragmentActivity
import java.util.*

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