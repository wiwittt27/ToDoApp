package com.alawiyaa.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alawiyaa.todoapp.data.source.local.entity.Task

interface ToDoDataSource {
    fun insertTask(task : Task)
    fun getListTask() : LiveData<PagedList<Task>>

}