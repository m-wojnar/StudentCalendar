package com.mwojnar.studentcalendar.intro

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import com.mwojnar.studentcalendar.R

class StudentCalendarIntro : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isColorTransitionsEnabled = true
        setTransformer(AppIntroPageTransformerType.Fade)

        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.app_name),
            description = getString(R.string.introduction_1),
            imageDrawable = R.mipmap.ic_launcher_round,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_3)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.first_steps),
            description = getString(R.string.first_steps_text_2),
            imageDrawable = R.drawable.ic_baseline_notes_160,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_2)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.first_steps),
            description = getString(R.string.first_steps_text_3),
            imageDrawable = R.drawable.slide_3,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_4)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.first_steps),
            description = getString(R.string.first_steps_text_4),
            imageDrawable = R.drawable.slide_4,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_6)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.setup_your_day),
            description = getString(R.string.setup_your_day_5),
            imageDrawable = R.drawable.slide_5,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_4)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.setup_your_day),
            description = getString(R.string.setup_your_day_6),
            imageDrawable = R.drawable.slide_6,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_5)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.settings),
            description = getString(R.string.settings_7),
            imageDrawable = R.drawable.slide_7,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_7)
        ))
        addSlide(AppIntroFragment.newInstance(
            title = getString(R.string.app_name),
            description = getString(R.string.text_8),
            imageDrawable = R.drawable.ic_baseline_mood_160,
            backgroundColor = ContextCompat.getColor(applicationContext, R.color.app_color_4)
        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }
}