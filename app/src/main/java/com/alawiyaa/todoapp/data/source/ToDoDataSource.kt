package com.alawiyaa.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Delete
import androidx.room.Update
import com.alawiyaa.todoapp.data.source.local.entity.Task

interface ToDoDataSource {
    fun insertTask(task : Task)
    fun updateTask(task : Task)
    fun deleteTask(task : Task)
    fun getListTask() : LiveData<PagedList<Task>>


}