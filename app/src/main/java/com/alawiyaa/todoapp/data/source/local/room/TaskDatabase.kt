package com.alawiyaa.todoapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alawiyaa.todoapp.data.source.local.entity.Task

@Database(entities = [Task::class], version = 1,exportSchema = false)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDao(): TaskDao
    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "db_todo"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }

}