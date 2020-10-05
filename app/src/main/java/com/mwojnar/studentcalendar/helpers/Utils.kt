package com.mwojnar.studentcalendar.helpers

import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.mwojnar.studentcalendar.R

fun validateEmpty(fragment: Fragment, inputLayout: TextInputLayout, editText: TextView) =
    if (editText.text.isNullOrEmpty()) {
        inputLayout.error = fragment.getString(R.string.required)
        false
    } else {
        inputLayout.error = null
        true
    }

fun validateReminderInThePast(fragment: Fragment, inputLayout: TextInputLayout, reminderTime: Long) =
    if (System.currentTimeMillis() > reminderTime) {
        inputLayout.error = fragment.getString(R.string.reminder_in_the_past)
        false
    } else {
        inputLayout.error = null
        true
    }

fun String?.isEmpty() =
    this.isNullOrBlank()

fun nameAndTypeString(name: String, type: String?) =
    if (type != null) {
        "${name}: $type"
    } else {
        name
    }

fun getIdentifier(context: Context, name: String?, typeStringId: Int) =
    context.resources.getIdentifier(name, context.getString(typeStringId), context.packageName)