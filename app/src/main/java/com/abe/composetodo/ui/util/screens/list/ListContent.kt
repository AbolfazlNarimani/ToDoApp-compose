package com.abe.composetodo.ui.util.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abe.composetodo.R
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.data.modules.ToDoTask
import com.abe.composetodo.ui.Action
import com.abe.composetodo.ui.theme.ELEVATION
import com.abe.composetodo.ui.theme.LARGE_PADDING
import com.abe.composetodo.ui.theme.MEDIUM_PADDINGS
import com.abe.composetodo.ui.theme.PRIORITY_INDICATOR_SIZE
import com.abe.composetodo.ui.theme.SMALL_PADDING
import com.abe.composetodo.ui.theme.padding24
import com.abe.composetodo.ui.theme.taskItemBackgroundColor
import com.abe.composetodo.ui.util.RequestState
import com.abe.composetodo.ui.util.SearchAppBarState

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    onSwipeToDelete:(Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchAppBarState: SearchAppBarState,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    mediumPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
) {

    if (sortState is RequestState.Success) {

        when {

            (searchAppBarState == SearchAppBarState.TRIGGERED) -> {
                if (searchedTasks is RequestState.Success) {
                    HandelListContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandelListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                HandelListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }

            sortState.data == Priority.MEDIUM -> {
                HandelListContent(
                    tasks = mediumPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }

            sortState.data == Priority.HIGH -> {
                HandelListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }
    }
}

@Composable
fun HandelListContent(tasks: List<ToDoTask>,onSwipeToDelete:(Action, ToDoTask) -> Unit, navigateToTaskScreen: (taskId: Int) -> Unit) {

    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(tasks = tasks, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete:(Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Box(Modifier.padding(top = 54.dp)) {
        LazyColumn {
            items(
                items = tasks,
                key = { it.id }
            ) { task ->

                val dismissState = rememberDismissState()
                val degrees by animateFloatAsState(
                    targetValue = if (dismissState.targetValue == DismissValue.Default) 0F else -45F,
                    label = "SwipeToDeleteAnimation"
                )
                val dismissDirection = dismissState.dismissDirection
                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
                if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                    onSwipeToDelete(Action.DELETE, task)
                }
                SwipeToDismiss(
                    state = dismissState,
                    background = { RedBackGround(degree = degrees) },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        TaskItem(toDoTask = task, navigateToTaskScreen = navigateToTaskScreen)
                    }
                )
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



    //some
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
fun RedBackGround(degree: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE91E63),
                        Color(0xFF880E4F)
                    )
                )
            )
            .padding(horizontal = padding24),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degree)
        )
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