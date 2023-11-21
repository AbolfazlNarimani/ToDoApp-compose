package com.abe.composetodo.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abe.composetodo.ui.Action
import com.abe.composetodo.ui.util.Constants
import com.abe.composetodo.ui.util.Constants.TASK_ARGUMENT_KEY
import com.abe.composetodo.ui.util.screens.task.TaskScreen
import com.abe.composetodo.viewmodels.SharedViewModel

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId = taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        TaskScreen(navigateToListScreen = navigateToListScreen, selectedTask = selectedTask)
    }
}