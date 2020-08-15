package com.broprojects.studentcalendar.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R
import kotlinx.coroutines.*
import kotlin.random.Random

class WelcomeViewModel: ViewModel() {
    // Application colors
    private val colors = listOf(
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
    private val drawables = listOf(
        R.drawable.ic_baseline_beach_access_140,
        R.drawable.ic_baseline_emoji_food_beverage_140,
        R.drawable.ic_baseline_local_cafe_140,
        R.drawable.ic_baseline_mood_140,
        R.drawable.ic_baseline_music_note_140,
        R.drawable.ic_baseline_thumb_up_140,
        R.drawable.ic_baseline_wb_sunny_140
    )
    private val _drawable = MutableLiveData<Int>()
    val drawable: LiveData<Int>
        get() = _drawable

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
        _drawable.value = drawables[Random.nextInt(drawables.size)]

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
}