package com.abe.composetodo.ui.util

import androidx.compose.ui.unit.dp

object Constants {
    const val ADD_TASK_SCREEN: String = "add_task_screen"
    const val DATABASE_TABLE = "todo-table"
    const val DATABASE_NAME = "todo_database"

    const val LIST_SCREEN = "list/{action}"
    const val TASK_SCREEN = "task/{taskId}"

    const val LIST_ARGUMENT_KEY = "action"
    const val TASK_ARGUMENT_KEY = "taskId"

    val PRIORITY_DROP_DOWN_HEIGHT = 60.dp

    const val MAX_TITLE_LENGTH = 20
    const val SPLASH_SCREEN_DELAY = 3000

    const val PREFERENCE_NAME = "todo_preference"
    const val PREFERENCE_KEY = "sort_state"
}