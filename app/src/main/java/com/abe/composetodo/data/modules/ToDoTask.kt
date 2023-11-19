package com.abe.composetodo.data.modules

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abe.composetodo.ui.util.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    val description: String = "",
    val priority: Priority,
    val time: String = "",
    val date: String = ""
)
