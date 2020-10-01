package com.mwojnar.studentcalendar.settings

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwojnar.studentcalendar.database.BackupClass
import com.mwojnar.studentcalendar.database.CalendarDatabase
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsViewModel(private val context: Context) : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _importSucceeded = MutableLiveData<Boolean>()
    val importSucceeded: LiveData<Boolean>
        get() = _importSucceeded

    private val _exportSucceeded = MutableLiveData<Boolean>()
    val exportSucceeded: LiveData<Boolean>
        get() = _exportSucceeded

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun importData(uri: Uri?) {
        if (uri == null) {
            _importSucceeded.value = false
            return
        }

        val inputStream = context.contentResolver.openInputStream(uri)
        if (inputStream == null) {
            _importSucceeded.value = false
            return
        }

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val json = inputStream.bufferedReader().use { it.readText() }
                    jsonToDatabase(json)
                } catch (exception: Exception) {
                    Log.e("SettingsFragment", "Exception", exception)
                    _importSucceeded.postValue(false)
                    return@withContext
                }

                _importSucceeded.postValue(true)
            }
        }
    }

    private fun jsonToDatabase(json: String) {
        val backupClass = Json.decodeFromString<BackupClass>(json)
        val database = CalendarDatabase.getInstance(context)
        backupClass.courses.forEach { database.coursesTableDao.insert(it) }
        backupClass.people.forEach { database.peopleTableDao.insert(it) }
        backupClass.schedules.forEach { database.schedulesTableDao.insert(it) }
        backupClass.tests.forEach { database.testsTableDao.insert(it) }
        backupClass.tasks.forEach { database.tasksTableDao.insert(it) }
    }

    fun exportData(directory: Uri?) {
        if (directory == null) {
            _exportSucceeded.value = false
            return
        }

        val documentFile = DocumentFile.fromTreeUri(context, directory)
        val uri = documentFile?.createFile("application/json", "student_calendar_data.json")?.uri
        if (uri == null) {
            _exportSucceeded.value = false
            return
        }

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val json = databaseToJson()
                    context.contentResolver.openOutputStream(uri)?.apply {
                        write(json.toByteArray())
                        flush()
                        close()
                    }
                } catch (exception: Exception) {
                    Log.e("SettingsFragment", "Exception", exception)
                    _exportSucceeded.postValue(false)
                    return@withContext
                }

                _exportSucceeded.postValue(true)
            }
        }
    }

    private fun databaseToJson(): String {
        val database = CalendarDatabase.getInstance(context)
        val backupClass = BackupClass(
            database.coursesTableDao.getAll() ?: listOf(),
            database.peopleTableDao.getAll() ?: listOf(),
            database.schedulesTableDao.getAll() ?: listOf(),
            database.tasksTableDao.getAll() ?: listOf(),
            database.testsTableDao.getAll() ?: listOf()
        )

        return Json.encodeToString(backupClass)
    }
}