package com.andruid.magic.dailytasks.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toEpochMillis(): Long {
    return atZone(ZoneId.systemDefault())
        .toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.withFirstDayOfWeek(): LocalDate {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    return with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.withLastDayOfWeek(): LocalDate {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    return with(TemporalAdjusters.next(firstDayOfWeek))
}