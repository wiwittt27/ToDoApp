package com.alawiyaa.todoapp.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String? = null,
    var desc : String? = null,
    var startTime: String? = null,
    var status: String? =  null
) : Parcelable