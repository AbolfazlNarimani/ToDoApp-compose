package com.abe.composetodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.abe.composetodo.navigation.destinations.listComposable
import com.abe.composetodo.navigation.destinations.taskComposable
import com.abe.composetodo.ui.util.Constants.LIST_SCREEN
import com.abe.composetodo.viewmodels.SharedViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screens = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screens.task,
            sharedViewModel = sharedViewModel
            )
        taskComposable(
            navigateToListScreen = screens.list,
            sharedViewModel = sharedViewModel
        )


    }
}