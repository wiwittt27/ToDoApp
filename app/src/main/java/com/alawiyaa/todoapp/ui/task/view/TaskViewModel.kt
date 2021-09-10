package com.alawiyaa.todoapp.ui.task.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alawiyaa.todoapp.data.source.ToDoRepository
import com.alawiyaa.todoapp.data.source.local.entity.Task

class TaskViewModel(private val mToDoRepository : ToDoRepository):ViewModel() {

    fun getAllTask() : LiveData<PagedList<Task>> {
        return mToDoRepository.getListTask()
    }
}