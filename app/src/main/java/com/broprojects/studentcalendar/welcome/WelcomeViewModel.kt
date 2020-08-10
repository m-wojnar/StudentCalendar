package com.broprojects.studentcalendar.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R
import kotlinx.coroutines.*
import kotlin.random.Random

class WelcomeViewModel: ViewModel() {
    private val colors = listOf(
        R.color.appColor1,
        R.color.appColor2,
        R.color.appColor3,
        R.color.appColor4,
        R.color.appColor5,
        R.color.appColor6,
        R.color.appColor7,
        R.color.appColor8
    )
    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int>
        get() = _color

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

    private val texts = listOf(
        R.string.welcome1,
        R.string.welcome2,
        R.string.welcome3,
        R.string.welcome4,
        R.string.welcome5,
        R.string.welcome6,
        R.string.welcome7
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
        _text.value = texts[Random.nextInt(texts.size)]
        _color.value = colors[Random.nextInt(colors.size)]
        _drawable.value = drawables[Random.nextInt(drawables.size)]

        coroutineScope.launch {
            withContext(Dispatchers.Default) {
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