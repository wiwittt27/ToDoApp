package com.alawiyaa.todoapp.util.timepickerinput

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(ms: Long): String {
    val localTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())

    return localTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun getCurrentHourMinutes(): Pair<Int, Int> {
    return LocalTime.now().run {
        hour to minute
    }
}