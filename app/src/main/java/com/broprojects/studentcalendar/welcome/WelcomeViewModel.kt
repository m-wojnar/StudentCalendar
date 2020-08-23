package com.broprojects.studentcalendar.welcome

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.broprojects.studentcalendar.R
import kotlinx.coroutines.*
import kotlin.random.Random

class WelcomeViewModel(private val activity: Activity): ViewModel() {

    companion object {
        // Static field to keep info if welcome screen was already showed
        var firstWelcome = true
    }

    // Application colors
    private val colors = arrayOf(
        R.color.app_color_1,
        R.color.app_color_2,
        R.color.app_color_3,
        R.color.app_color_4,
        R.color.app_color_5,
        R.color.app_color_6,
        R.color.app_color_7,
        R.color.app_color_8
    )
    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int>
        get() = _color

    // Application icons
    private val icons = arrayOf(
        R.drawable.ic_baseline_beach_access_140,
        R.drawable.ic_baseline_emoji_food_beverage_140,
        R.drawable.ic_baseline_local_cafe_140,
        R.drawable.ic_baseline_mood_140,
        R.drawable.ic_baseline_music_note_140,
        R.drawable.ic_baseline_thumb_up_140,
        R.drawable.ic_baseline_wb_sunny_140
    )
    private val _icon = MutableLiveData<Int>()
    val icon: LiveData<Int>
        get() = _icon

    // Application welcome texts
    private val texts = listOf(
        R.string.welcome_1,
        R.string.welcome_2,
        R.string.welcome_3,
        R.string.welcome_4,
        R.string.welcome_5,
        R.string.welcome_6,
        R.string.welcome_7
    )
    private val _text = MutableLiveData<Int>()
    val text: LiveData<Int>
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
            putInt(activity.getString(R.string.random_welcome_text), _text.value!!)
            putInt(activity.getString(R.string.random_welcome_color), _color.value!!)
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
        viewModelJob.cancel()
    }

    fun hideActionBarWithAnimation(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity.applicationContext)
        val showWelcome = preferences.getBoolean(activity.getString(R.string.show_welcome), true)
        return showWelcome && firstWelcome
    }

    fun hideActionBarDone() {
        firstWelcome = false
    }
}