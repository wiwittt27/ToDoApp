package com.alawiyaa.todoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alawiyaa.todoapp.data.source.ToDoRepository

class HomeViewModel(private val mToDoRepository : ToDoRepository): ViewModel() {
    fun getCountTask(date : String): LiveData<Int> {
        return mToDoRepository.getCountTask(date)
    }
}