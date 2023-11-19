package com.abe.composetodo.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abe.composetodo.ui.util.Constants.LIST_ARGUMENT_KEY
import com.abe.composetodo.ui.util.Constants.LIST_SCREEN
import com.abe.composetodo.ui.util.screens.list.ListScreen

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){
        ListScreen(onFabClicked = navigateToTaskScreen)
    }
}