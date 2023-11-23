package com.abe.composetodo.data.modules.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abe.composetodo.data.modules.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(toDoTask: ToDoTask)
    @Update
    suspend fun updateTask(toDoTask: ToDoTask)
    @Delete
    suspend fun delete(toDoTask: ToDoTask)

    @Query("DELETE FROM `todo-table`")
    suspend fun deleteAll()
    @Query("SELECT * FROM `todo-table` ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>
    @Query("SELECT * FROM `todo-table` WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>
    @Query("SELECT * FROM `todo-table` WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM `todo-table` ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 WHEN priority LIKE 'N%' THEN 4 END")
    fun sortByHighPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM `todo-table` ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 WHEN priority LIKE 'N%' THEN 4 END")
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM `todo-table` ORDER BY CASE WHEN priority LIKE 'M%' THEN 1 WHEN priority LIKE 'L%' THEN 2 WHEN priority LIKE 'H%' THEN 3 WHEN priority LIKE 'N%' THEN 4 END")
    fun sortByMediumPriority(): Flow<List<ToDoTask>>
}