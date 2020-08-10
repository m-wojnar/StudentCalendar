package com.broprojects.studentcalendar.database.settings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.broprojects.studentcalendar.database.settings.Settings

@Dao
interface SettingsDAO {

    //Nie ogarniam jak to działa, ale tu da się jakoś rozdzielić żeby pojedyncze ustawienia zmieniać?
    @Insert
    fun insert (settings: Settings)

    @Update
    fun update (settings: Settings)

    @Delete
    fun delete (settings: Settings)
}