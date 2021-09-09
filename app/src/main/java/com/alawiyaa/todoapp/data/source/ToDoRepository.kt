package com.alawiyaa.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alawiyaa.todoapp.data.source.local.LocalDataSource
import com.alawiyaa.todoapp.data.source.local.entity.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoRepository private constructor(private val localDataSource: LocalDataSource) : ToDoDataSource{

    companion object {
        @Volatile
        private var instance: ToDoRepository? = null

        fun getInstance( localDataSource: LocalDataSource): ToDoRepository =
            instance ?: synchronized(this) {
                instance ?: ToDoRepository(localDataSource)
            }
    }

    override fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertTask(task)
        }
    }

    override fun getListTask(): LiveData<PagedList<Task>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getListTask(), config).build()
    }


}