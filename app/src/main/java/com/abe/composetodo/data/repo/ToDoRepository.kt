package com.abe.composetodo.data.repo

import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.data.modules.interfaces.ToDoDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTasks = toDoDao.getAllTasks()
    val sortByLowPriority = toDoDao.sortByLowPriority()
    val sortByMediumPriority = toDoDao.sortByMediumPriority()
    val sortByHighPriority = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId:Int):Flow<ToDoTask>{
        return toDoDao.getSelectedTask(taskId)
    }

    suspend fun insertTask(toDoTask: ToDoTask) {
        toDoDao.insert(toDoTask = toDoTask)
    }
    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask = toDoTask)
    }
    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.delete(toDoTask)
    }
    suspend fun deleteAllTask() {
        toDoDao.deleteAll()
    }
    fun searchDatabase(query:String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(query)
    }
}