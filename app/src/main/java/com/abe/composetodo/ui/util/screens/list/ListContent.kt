package com.abe.composetodo.ui.util.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.ui.theme.ELEVATION
import com.abe.composetodo.ui.theme.LARGE_PADDING
import com.abe.composetodo.ui.theme.MEDIUM_PADDINGS
import com.abe.composetodo.ui.theme.PRIORITY_INDICATOR_SIZE
import com.abe.composetodo.ui.theme.SMALL_PADDING
import com.abe.composetodo.ui.theme.taskItemBackgroundColor
import com.abe.composetodo.ui.util.RequestState
import com.abe.composetodo.ui.util.SearchAppBarState

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchAppBarState: SearchAppBarState
) {
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        if (searchedTasks is RequestState.Success) {
            HandelListContent(
                tasks = searchedTasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    } else {
       if (allTasks is RequestState.Success) {
           HandelListContent(
               tasks = allTasks.data,
               navigateToTaskScreen = navigateToTaskScreen
           )
       }
    }
}

@Composable
fun HandelListContent(tasks: List<ToDoTask>, navigateToTaskScreen: (taskId: Int) -> Unit) {

    if (tasks.isEmpty()) {
        EmptyContent()
    }else {
        DisplayTasks(tasks = tasks, navigateToTaskScreen = navigateToTaskScreen)
    }
}

@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Box(Modifier.padding(top = 54.dp)) {
        LazyColumn {
            items(tasks, key = { task -> task.id }) { task ->
                TaskItem(toDoTask = task, navigateToTaskScreen = navigateToTaskScreen)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToTaskScreen(toDoTask.id) }
            .padding(top = 1.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = taskItemBackgroundColor,
            shape = RectangleShape,
            shadowElevation = ELEVATION,
            onClick = { navigateToTaskScreen(toDoTask.id) }
        )
        {
            Column(
                modifier = Modifier
                    .padding(LARGE_PADDING)
                    .fillMaxWidth()
            ) {

                Row {
                    Text(
                        text = toDoTask.title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.weight(8F)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F), contentAlignment = Alignment.TopEnd
                    ) {
                        Canvas(
                            modifier = Modifier
                                .size(PRIORITY_INDICATOR_SIZE)
                        ) {
                            drawCircle(
                                color = toDoTask.priority.color
                            )
                        }
                    }
                }
                Row {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MEDIUM_PADDINGS, top = SMALL_PADDING),
                        text = toDoTask.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row {
                    Text(
                        modifier = Modifier
                            .padding(end = MEDIUM_PADDINGS)
                            .weight(8F),
                        text = toDoTask.time,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )
                    Text(
                        text = toDoTask.date,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )
                }


            }
        }
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(toDoTask = ToDoTask(
        0,
        "Tets",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        Priority.HIGH,
        "23:32",
        "2023"
    ), navigateToTaskScreen = {})
}