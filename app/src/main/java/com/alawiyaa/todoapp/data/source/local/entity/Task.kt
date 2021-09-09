package com.alawiyaa.todoapp.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alawiyaa.todoapp.util.STATUS_PENDING
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String? = null,
    var desc : String? = null,
    var startTime: Long = 0,
    var endTime: Long = 0,
    var day: Int? = 0,
    var month: Int? = 0,
    var year: Int? = 0,
    var status: String? =  null
) : Parcelable