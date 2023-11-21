package com.abe.composetodo.ui.util.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.abe.composetodo.R
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.ui.Action

@Composable
fun TaskAppBar(navigateToListScreen : (Action) -> Unit, selectedTask: ToDoTask?) {


    when (selectedTask){
        null -> {
            NewTaskAppBar(navigateToListScreen = navigateToListScreen)
        }
        else -> {
            ExistingTaskAppBar(
                navigateToListScreen = navigateToListScreen,
                selectedTask = selectedTask
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen : (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigateToListScreen)},
        title = { Text(text = stringResource(R.string.add_task)) },
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )
}
@Composable
fun BackAction(onBackClicked: (Action) -> Unit) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_arrow),modifier = Modifier.alpha(0.6F))
    }
}
@Composable
fun AddAction(onAddClicked: (Action) -> Unit) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task),modifier = Modifier.alpha(0.6F))
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(navigateToListScreen: (Action) -> Unit,selectedTask : ToDoTask) {

    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToListScreen) },
        title = { Text(text = selectedTask.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        actions = {
            DeleteAction(onDeleteClicked = navigateToListScreen)
            UpdateAction(onUpdateClicked = navigateToListScreen)
        }
    )
}

@Composable
fun UpdateAction(onUpdateClicked: (Action) -> Unit) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(imageVector = Icons.Filled.Refresh, contentDescription = stringResource(R.string.update),modifier = Modifier.alpha(0.6F))
    }
}

@Composable
fun DeleteAction(onDeleteClicked: (Action) -> Unit) {
    IconButton(onClick = { onDeleteClicked(Action.DELETE) }) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(R.string.delete),modifier = Modifier.alpha(0.6F))
    }
}

@Composable
fun CloseAction(onCloseClicked: (Action) -> Unit) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.close),modifier = Modifier.alpha(0.6F))
    }
}

@Composable
@Preview
fun NewTaskAppBarPreview(){
    NewTaskAppBar({})
}

@Composable
@Preview
fun ExistingTaskBarPreview(){
   ExistingTaskAppBar(navigateToListScreen = {}, selectedTask = ToDoTask(id = 0, title = "Blyad", description = "Cyka", date = "2023", time = "20:30", priority = Priority.MEDIUM))
}