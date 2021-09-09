package com.alawiyaa.todoapp.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alawiyaa.todoapp.data.source.local.entity.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY status DESC")
    fun getListTask() : DataSource.Factory<Int, Task>
}