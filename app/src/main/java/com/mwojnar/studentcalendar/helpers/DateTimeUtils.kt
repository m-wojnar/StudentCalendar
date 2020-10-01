package com.mwojnar.studentcalendar.helpers

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import androidx.fragment.app.FragmentActivity
import androidx.room.TypeConverter
import com.mwojnar.studentcalendar.R
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

fun FragmentActivity.dateTimePickerDialog(func: (date: Date) -> Unit) {
    val currentDateTime = Calendar.getInstance()

    DatePickerDialog(this, { _, year, month, day ->
        TimePickerDialog(this, { _, hour, min ->
            val dateTime =  Calendar.getInstance()
            dateTime.set(year, month, day, hour, min)
            func(dateTime.time)
        }, currentDateTime[Calendar.HOUR_OF_DAY], currentDateTime[Calendar.MINUTE], true).show()
    }, currentDateTime[Calendar.YEAR], currentDateTime[Calendar.MONTH], currentDateTime[Calendar.DATE]).show()
}

fun FragmentActivity.datePickerDialog(func: (date: Date) -> Unit) {
    val currentDateTime = Calendar.getInstance()

    DatePickerDialog(this, { _, year, month, day ->
        val dateTime =  Calendar.getInstance()
        dateTime.set(year, month, day, 0, 0)
        func(dateTime.time)
    }, currentDateTime[Calendar.YEAR], currentDateTime[Calendar.MONTH], currentDateTime[Calendar.DATE]).show()
}

fun FragmentActivity.timePickerDialog(func: (date: Date) -> Unit) {
    val currentDateTime = Calendar.getInstance()

    TimePickerDialog(this, { _, hour, min ->
        val dateTime =  Calendar.getInstance()
        dateTime.set(currentDateTime[Calendar.YEAR], currentDateTime[Calendar.MONTH], currentDateTime[Calendar.DATE], hour, min)
        func(dateTime.time)
    }, currentDateTime[Calendar.HOUR_OF_DAY], currentDateTime[Calendar.MINUTE], true).show()
}

fun Date.toDateString(context: Context): String {
    val dateFormat = DateFormat.getDateFormat(context)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(this)
}

fun Date.toTimeString(context: Context): String {
    val timeFormat = DateFormat.getTimeFormat(context)
    timeFormat.timeZone = TimeZone.getDefault()
    return timeFormat.format(this)
}

fun Date.toDateTimeString(context: Context): String {
    val dateFormat = DateFormat.getDateFormat(context)
    dateFormat.timeZone = TimeZone.getDefault()
    val timeFormat = DateFormat.getTimeFormat(context)
    timeFormat.timeZone = TimeZone.getDefault()
    return context.getString(R.string.date_time, dateFormat.format(this), timeFormat.format(this))
}

fun Date.getDayTime(): Int {
    val date = Calendar.getInstance()
    date.time = this

    return date[Calendar.HOUR_OF_DAY] * 60 * 60 + date[Calendar.MINUTE] * 60 + date[Calendar.SECOND]
}

class Converters {
    @TypeConverter
    fun dateToLong(date: Date?) = date?.time

    @TypeConverter
    fun longToDate(long: Long?) = long?.let { Date(it) }
}

@Serializer(forClass = Date::class)
object DateSerializer: KSerializer<Date> {
    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeLong(value.time)
    }

    override fun deserialize(decoder: Decoder) =
        Date(decoder.decodeLong())
}