package com.alawiyaa.todoapp.ui.task.add

import androidx.lifecycle.ViewModel
import com.alawiyaa.todoapp.data.source.ToDoRepository
import com.alawiyaa.todoapp.data.source.local.entity.Task

class AddTaskViewModel(private val mToDoRepository : ToDoRepository) : ViewModel() {
    fun insertTask(task: Task) {
        mToDoRepository.insertTask(task)
    }
}