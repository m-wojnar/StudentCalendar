package com.broprojects.studentcalendar.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R
import kotlinx.coroutines.*
import kotlin.random.Random

class WelcomeViewModel: ViewModel() {
    private val numOfColors = 7
    private val colors = listOf<Int>(
        R.color.appColor1,
        R.color.appColor2,
        R.color.appColor3,
        R.color.appColor4,
        R.color.appColor5,
        R.color.appColor6,
        R.color.appColor7
    )

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int>
        get() = _color

    private val numOfTexts = 7
    private val texts = listOf<Int>(
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
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _text.value = texts[Random.nextInt(numOfTexts)]
        _color.value = colors[Random.nextInt(numOfColors)]

        uiScope.launch {
            withContext(Dispatchers.Default) {
                Thread.sleep(welcomeScreenTime)
                _mainFragmentEvent.postValue(true)
            }
        }
    }

    fun mainFragmentEventDone() {
        _mainFragmentEvent.value = false
    }
}