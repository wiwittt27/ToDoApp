package com.alawiyaa.todoapp.data.source.local

import androidx.paging.DataSource
import com.alawiyaa.todoapp.data.source.local.entity.Task
import com.alawiyaa.todoapp.data.source.local.room.TaskDao

class LocalDataSource constructor(private val mTaskDao: TaskDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(mTaskDao: TaskDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(mTaskDao)
    }

     fun insertTask(task: Task) = mTaskDao.insertTask(task)
     fun updateTask(task: Task) = mTaskDao.update(task)
     fun deleteTask(task: Task) = mTaskDao.delete(task)
    fun getListTask() : DataSource.Factory<Int, Task> = mTaskDao.getListTask()
    fun getCountTask( )  = mTaskDao.getStatusTasksCount()


}