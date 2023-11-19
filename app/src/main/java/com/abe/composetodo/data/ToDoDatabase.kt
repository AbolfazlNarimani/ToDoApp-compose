package com.abe.composetodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.data.modules.interfaces.ToDoDao

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase(){
    abstract fun toDoDao(): ToDoDao
}