package com.abe.composetodo.ui

import java.util.Locale

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

fun String?.toAction(): Action {
    return when  {
        this == "ADD.name" -> Action.ADD
        this == "UPDATE.name" -> Action.UPDATE
        this == "DELETE.name" -> Action.DELETE
        this == "DELETE_ALL.name" -> Action.DELETE_ALL
        this == "UNDO.name" -> Action.UNDO
        else -> Action.NO_ACTION
    }
}