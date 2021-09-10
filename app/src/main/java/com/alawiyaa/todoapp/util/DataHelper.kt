package com.alawiyaa.todoapp.util

import android.app.TimePickerDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DataHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }


}