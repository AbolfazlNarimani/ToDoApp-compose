package com.abe.composetodo.ui.util.screens.task

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.ui.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(navigateToListScreen : (Action) -> Unit, selectedTask: ToDoTask?){
    Scaffold(
        topBar = { TaskAppBar(navigateToListScreen = navigateToListScreen, selectedTask = selectedTask)},
        content = {},
    )
}