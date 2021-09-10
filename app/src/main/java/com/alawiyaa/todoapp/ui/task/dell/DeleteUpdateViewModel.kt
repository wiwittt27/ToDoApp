package com.alawiyaa.todoapp.ui.task.dell

import androidx.lifecycle.ViewModel
import com.alawiyaa.todoapp.data.source.ToDoRepository
import com.alawiyaa.todoapp.data.source.local.entity.Task

class DeleteUpdateViewModel(private val mToDoRepository : ToDoRepository):ViewModel() {

    fun updateTask(task: Task) {
        mToDoRepository.updateTask(task)
    }
    fun deleteTask(task: Task) {
        mToDoRepository.deleteTask(task)
    }
}