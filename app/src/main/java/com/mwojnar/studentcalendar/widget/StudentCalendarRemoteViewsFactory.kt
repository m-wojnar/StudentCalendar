package com.mwojnar.studentcalendar.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.CalendarDatabase
import com.mwojnar.studentcalendar.database.YourDayItem
import java.util.*

class StudentCalendarRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private val yourDayList = mutableListOf<YourDayItem>()

    override fun onDataSetChanged() {
        // Load data from database
        val database = CalendarDatabase.getInstance(context)

        val today = Calendar.getInstance()
        today.set(today[Calendar.YEAR], today[Calendar.MONTH], today[Calendar.DATE], 0, 0)
        val tomorrow = Calendar.getInstance()
        tomorrow.set(today[Calendar.YEAR], today[Calendar.MONTH], today[Calendar.DATE], 0, 0)
        tomorrow.add(Calendar.DATE, 1)

        yourDayList.clear()

        yourDayList.addAll(
            database.schedulesTableDao
                .getYourDayItems(today.time, today[Calendar.DAY_OF_WEEK])
                .map { it.toYourDayItem(context) }
        )
        yourDayList.addAll(
            database.tasksTableDao
                .getYourDayItems(today.time, tomorrow.time)
                .map { it.toYourDayItem(context) }
        )
        yourDayList.addAll(
            database.testsTableDao
                .getYourDayItems(today.time, tomorrow.time)
                .map { it.toYourDayItem(context) }
        )

        yourDayList.sortBy { it.whenDateTime?.time }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val course = yourDayList[position].course

        val colorId =
            if (course?.colorName != null) {
                context.resources.getIdentifier(
                    course.colorName,
                    context.getString(R.string.type_color),
                    context.packageName
                )
            } else {
                R.color.recycler_view_item
            }

        val iconId =
            if (course?.iconName != null) {
                context.resources.getIdentifier(
                    course.iconName,
                    context.getString(R.string.type_drawable),
                    context.packageName
                )
            } else {
                android.R.color.transparent
            }

        // Style list item and fill text fields
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_your_day_item)
        remoteViews.setTextViewText(R.id.title_text, yourDayList[position].title)
        remoteViews.setTextViewText(R.id.when_text, yourDayList[position].whenText)
        remoteViews.setImageViewResource(R.id.course_icon, iconId)
        remoteViews.setInt(R.id.course_icon, "setColorFilter", android.R.color.white)
        remoteViews.setInt(R.id.your_day_item, "setBackgroundResource", colorId)

        return remoteViews
    }

    override fun getCount() =
        yourDayList.size

    override fun getItemId(position: Int) =
        yourDayList[position].itemId!!.toLong()

    // Unnecessary methods
    override fun onCreate() {}
    override fun getLoadingView() = null
    override fun getViewTypeCount() = 1
    override fun hasStableIds() = true
    override fun onDestroy() {}
}
