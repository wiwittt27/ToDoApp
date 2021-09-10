package com.alawiyaa.todoapp.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.alawiyaa.todoapp.data.source.local.entity.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY status DESC")
    fun getListTask() : DataSource.Factory<Int, Task>

    @Update
    fun update(note: Task)
    @Delete
    fun delete(note: Task)
}