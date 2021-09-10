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
    var startTime: String? = null,
    var endTime: String? = null,
    var date: String? = null,
    var status: Int = STATUS_PENDING
) : Parcelable