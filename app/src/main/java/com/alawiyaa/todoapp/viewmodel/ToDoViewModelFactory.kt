package com.alawiyaa.todoapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alawiyaa.todoapp.data.source.ToDoRepository
import com.alawiyaa.todoapp.di.Injection
import com.alawiyaa.todoapp.ui.task.view.TaskViewModel
import com.alawiyaa.todoapp.ui.task.add.AddTaskViewModel
import com.alawiyaa.todoapp.ui.task.dell.DeleteUpdateViewModel

class ToDoViewModelFactory private constructor(private val mTodoRepository: ToDoRepository) :  ViewModelProvider.NewInstanceFactory(){
    companion object {
        @Volatile
        private var instance: ToDoViewModelFactory? = null

        fun getInstance(context: Context): ToDoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ToDoViewModelFactory(Injection.provideCatalogRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddTaskViewModel::class.java) -> {
                AddTaskViewModel(mTodoRepository) as T
            }
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                TaskViewModel(mTodoRepository) as T
            }
            modelClass.isAssignableFrom(DeleteUpdateViewModel::class.java) -> {
                DeleteUpdateViewModel(mTodoRepository) as T
            }




            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}