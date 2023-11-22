package com.abe.composetodo.ui.util.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.ui.Action
import com.abe.composetodo.viewmodels.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel
) {

    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority
    val date by sharedViewModel.date
    val time by sharedViewModel.time

    val context = LocalContext.current




    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = {
                    if (it == Action.NO_ACTION) {
                       navigateToListScreen(it)
                    }else{
                        if (sharedViewModel.validateFields()){
                            navigateToListScreen(it)
                        }else{
                            displayToast(context = context)
                        }
                    }
                },
                selectedTask = selectedTask
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = { sharedViewModel.updateTitle(it) },
                description = description,
                onDescriptionChange = { sharedViewModel.description.value = it },
                priority = priority,
                onPrioritySelected = { sharedViewModel.priority.value = it },
                date = date,
                onDateSelected = { sharedViewModel.date.value = it },
                time = time,
                onTimeSelected = { sharedViewModel.time.value = it }
            )
        },
    )
}

fun displayToast(context: Context) {
    Toast.makeText(context,"Fill all fields",Toast.LENGTH_SHORT).show()
}
