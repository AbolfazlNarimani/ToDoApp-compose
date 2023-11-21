package com.abe.composetodo.viewmodels

import android.adservices.adid.AdId
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.data.repo.ToDoRepository
import com.abe.composetodo.ui.util.RequestState
import com.abe.composetodo.ui.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {

    private val _allTask =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTask

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")
        fun getAllTasks(){
            _allTask.value = RequestState.Loading

            try {
                viewModelScope.launch(Dispatchers.IO){
                    repository.getAllTasks.collect{
                        _allTask.value = RequestState.Success(it)
                    }
                }
            } catch (e: Exception) {
                _allTask.value = RequestState.Error(e)
            }
        }
    private val  _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask
    fun getSelectedTask(taskId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSelectedTask(taskId).collect(){
                task->
                _selectedTask.value = task
            }
        }
    }
    fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllTask()
        }
    }
}