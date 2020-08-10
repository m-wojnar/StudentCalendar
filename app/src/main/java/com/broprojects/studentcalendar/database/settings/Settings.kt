package com.broprojects.studentcalendar.database.settings

import androidx.room.Entity

@Entity(tableName = "Settings_table")
data class Settings(

    var name: String = "",
    var welcome: Int = 0,
    var theme: Int = 0
)
