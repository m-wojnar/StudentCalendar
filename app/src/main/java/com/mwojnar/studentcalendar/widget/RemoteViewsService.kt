package com.mwojnar.studentcalendar.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StudentCalendarWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StudentCalendarRemoteViewsFactory(this.applicationContext)
    }
}
