package com.abe.composetodo.navigation.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abe.composetodo.ui.Action
import com.abe.composetodo.ui.toAction
import com.abe.composetodo.ui.util.Constants.LIST_ARGUMENT_KEY
import com.abe.composetodo.ui.util.Constants.LIST_SCREEN
import com.abe.composetodo.ui.util.screens.list.ListScreen
import com.abe.composetodo.viewmodels.SharedViewModel

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {navBackStackEntry->

        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()


         LaunchedEffect(key1 = action){
             sharedViewModel.action.value = action
         }
        

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}