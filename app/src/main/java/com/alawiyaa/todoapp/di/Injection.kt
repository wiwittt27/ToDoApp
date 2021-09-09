package com.alawiyaa.todoapp.di

import android.content.Context

import com.alawiyaa.todoapp.data.source.ToDoRepository
import com.alawiyaa.todoapp.data.source.local.LocalDataSource
import com.alawiyaa.todoapp.data.source.local.room.TaskDatabase

object Injection {
    fun provideCatalogRepository(context: Context): ToDoRepository {
        val database = TaskDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.taskDao())
        return ToDoRepository.getInstance(localDataSource)
    }
}