package com.alawiyaa.todoapp.util.timepickerinput

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun getTimeString(millis: Long): String {
    val days = TimeUnit.MILLISECONDS.toDays(millis)
    if (days > 0)
        return "$days days"

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    if (hours > 0)
        return "$hours h"

    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    return "$minutes min"
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateDetails(millis: Long): Array<Int> {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())

    return arrayOf(
        dateTime.dayOfMonth,
        dateTime.monthValue,
        dateTime.year
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.showDayAndMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM")
    return format(formatter)
}