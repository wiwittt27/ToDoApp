package com.alawiyaa.todoapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DataHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun currentTime() : String{
        val dateValue = getCurrentDate()
        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault())

        var date: Date? = null
        try {
            date = sdf.parse(dateValue)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        return timeFormatter.format(date!!)
    }
}