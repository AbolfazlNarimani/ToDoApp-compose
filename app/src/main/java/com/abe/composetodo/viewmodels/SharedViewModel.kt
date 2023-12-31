package com.abe.composetodo.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.data.repo.DataStoreRepo
import com.abe.composetodo.data.repo.ToDoRepository
import com.abe.composetodo.ui.Action
import com.abe.composetodo.ui.util.Constants.MAX_TITLE_LENGTH
import com.abe.composetodo.ui.util.RequestState
import com.abe.composetodo.ui.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    // task fields
    val id: MutableState<Int> = mutableIntStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    val time: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf("")


    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    private var taskToDelete: ToDoTask? = null





    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")
    private val _searchTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchTasks
    fun searchDatabase(searchQuery: String) {
        _searchTasks.value = RequestState.Loading

        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.searchDatabase(query = "%$searchQuery%").collect {searchedTasks->
                    _searchTasks.value = RequestState.Success(searchedTasks)
                }
            }
        } catch (e: Exception) {
            _searchTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.persistSortState(priority = priority)
        }
    }
    private val _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    val lowPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val highPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val mediumPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByMediumPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val nonePriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByNonePriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepo.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }


            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    private val _allTask =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTask

    fun getAllTasks() {
        _allTask.value = RequestState.Loading

        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllTasks.collect {
                    _allTask.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTask.value = RequestState.Error(e)
        }
    }



    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask
    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSelectedTask(taskId).collect() { task ->
                _selectedTask.value = task
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value,
                time = time.value,
                date = date.value
            )
            repository.insertTask(toDoTask = toDoTask)
           searchAppBarState.value = SearchAppBarState.CLOSED
        }
    }
    private fun addTask(toDoTask: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(toDoTask = toDoTask)
            searchAppBarState.value = SearchAppBarState.CLOSED
        }
    }

    private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value,
                time = time.value,
                date = date.value
            )
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value,
                time = time.value,
                date = date.value
            )
            taskToDelete = toDoTask
            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
                updateTaskFields(null)
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTask()
            }

            Action.UNDO -> {
              undoDeleteTask()
            }
            else -> {}
        }
        this.action.value = Action.NO_ACTION
    }

    private fun undoDeleteTask() {
        taskToDelete?.let { addTask(it) }
    }

    fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null && selectedTask.id != -1) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
            time.value = selectedTask.time
            date.value = selectedTask.date
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
            time.value = ""
            date.value = ""
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty() && date.value.isNotEmpty() && time.value.isNotEmpty()
    }

    fun clearTaskFields() {
        id.value = 0
        title.value = ""
        description.value = ""
        priority.value = Priority.LOW
        time.value = ""
        date.value = ""
    }

}