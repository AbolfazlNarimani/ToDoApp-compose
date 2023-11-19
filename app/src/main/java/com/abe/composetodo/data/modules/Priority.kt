package com.abe.composetodo.data.modules

import androidx.compose.ui.graphics.Color
import com.abe.composetodo.ui.theme.LowPriorityColor
import com.abe.composetodo.ui.theme.highPriorityColor
import com.abe.composetodo.ui.theme.mediumPriorityColor
import com.abe.composetodo.ui.theme.nonePriorityColor

enum class Priority (val color: Color){
    HIGH(highPriorityColor),
    MEDIUM(mediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(nonePriorityColor)
}