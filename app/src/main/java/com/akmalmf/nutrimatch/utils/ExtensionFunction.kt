package com.akmalmf.nutrimatch.utils

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.core.domain.model.FoodRequestModel
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun EditText.onSubmit(func: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            func()
        }

        true
    }
}

fun TextInputLayout.getText(): String {
    return editText?.text.toString().trim()
}

fun TextInputLayout.setText(value: String) {
    editText?.setText(value)
}

fun String.toGenderClassification(): String {
    return when (this.uppercase()) {
        "M" -> {
            "Male"
        }
        else -> {
            "Female"
        }
    }
}

fun String.toActivitiesLevel(): String{
    return when (this.uppercase()) {
        "L" -> {
            "Low"
        }
        "M" -> {
            "Medium"
        }
        else -> {
            "High"
        }
    }
}

fun String.formatToDate(): String{
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", DateFormatSymbols.getInstance().apply { months = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December") })
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) } ?: ""
}

fun Long.convertToDate(locale: Locale = Locale("id", "ID")): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd ",locale )
    val date = Date(this)
    return dateFormat.format(date)
}

fun isValidEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

inline fun <reified T> Gson.fromJson(json:String): T =
    this.fromJson(json,object : TypeToken<T>(){}.type)


fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}