package com.mwojnar.studentcalendar.welcome

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.mwojnar.studentcalendar.MainActivity
import com.mwojnar.studentcalendar.R
import kotlinx.coroutines.*
import kotlin.random.Random

class WelcomeViewModel(private val activity: Activity): ViewModel() {

    // Application colors
    private val colors = arrayOf(
        getResourceName(R.color.app_color_1),
        getResourceName(R.color.app_color_2),
        getResourceName(R.color.app_color_3),
        getResourceName(R.color.app_color_4),
        getResourceName(R.color.app_color_5),
        getResourceName(R.color.app_color_6),
        getResourceName(R.color.app_color_7),
        getResourceName(R.color.app_color_8),
        getResourceName(R.color.app_color_9),
        getResourceName(R.color.app_color_10),
        getResourceName(R.color.app_color_11),
        getResourceName(R.color.app_color_12),
        getResourceName(R.color.app_color_13),
        getResourceName(R.color.app_color_14)
    )
    private val _color = MutableLiveData<String>()
    val color: LiveData<String>
        get() = _color

    // Application icons
    private val icons = arrayOf(
        getResourceName(R.drawable.ic_baseline_beach_access_24),
        getResourceName(R.drawable.ic_baseline_emoji_food_beverage_24),
        getResourceName(R.drawable.ic_baseline_local_cafe_24),
        getResourceName(R.drawable.ic_baseline_mood_24),
        getResourceName(R.drawable.ic_baseline_music_note_24),
        getResourceName(R.drawable.ic_baseline_thumb_up_24),
        getResourceName(R.drawable.ic_baseline_wb_sunny_24),
        getResourceName(R.drawable.ic_baseline_airplane_active_24),
        getResourceName(R.drawable.ic_baseline_android_24),
        getResourceName(R.drawable.ic_baseline_cake_24),
        getResourceName(R.drawable.ic_baseline_camera_24),
        getResourceName(R.drawable.ic_baseline_chat_24),
        getResourceName(R.drawable.ic_baseline_laptop_24),
        getResourceName(R.drawable.ic_baseline_leaf_24),
        getResourceName(R.drawable.ic_baseline_notes_24)
    )
    private val _icon = MutableLiveData<String>()
    val icon: LiveData<String>
        get() = _icon

    // Application welcome texts
    private val texts = listOf(
        getResourceName(R.string.welcome_1),
        getResourceName(R.string.welcome_2),
        getResourceName(R.string.welcome_3),
        getResourceName(R.string.welcome_4),
        getResourceName(R.string.welcome_5),
        getResourceName(R.string.welcome_6),
        getResourceName(R.string.welcome_7)
    )
    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val welcomeScreenTime = 2500L
    private val _mainFragmentEvent = MutableLiveData<Boolean>()
    val mainFragmentEvent: LiveData<Boolean>
        get() = _mainFragmentEvent

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        // Choose random color, icon and welcome text
        _text.value = texts[Random.nextInt(texts.size)]
        _color.value = colors[Random.nextInt(colors.size)]
        _icon.value = icons[Random.nextInt(icons.size)]

        // Save randomly chosen values to shared preferences
        val sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        with (sharedPreferences.edit()) {
            putString(activity.getString(R.string.random_welcome_text_name), _text.value!!)
            putString(activity.getString(R.string.random_welcome_color_name), _color.value!!)
            apply()
        }

        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                // Wait welcomeScreenTime millis before going to main fragment
                delay(welcomeScreenTime)
                goToMainFragment()
            }
        }
    }

    fun goToMainFragment() {
        _mainFragmentEvent.postValue(true)
    }

    fun goToMainFragmentDone() {
        _mainFragmentEvent.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun hideActionBarWithAnimation(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity.applicationContext)
        val showWelcome = preferences.getBoolean(activity.getString(R.string.show_welcome), true)
        return showWelcome && MainActivity.firstWelcome
    }

    fun hideActionBarDone() {
        MainActivity.firstWelcome = false
    }

    private fun getResourceName(id: Int) =
        activity.resources.getResourceEntryName(id)
}