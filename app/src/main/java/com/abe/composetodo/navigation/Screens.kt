package com.abe.composetodo.navigation

import androidx.navigation.NavHostController
import com.abe.composetodo.ui.Action
import com.abe.composetodo.ui.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = {
        navController.navigate("list/$it.name") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task: (Int) -> Unit = {
        navController.navigate("task/$it")
    }
}