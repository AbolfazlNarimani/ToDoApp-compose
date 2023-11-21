package com.abe.composetodo.ui.theme

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LowPriorityColor = Color(0xFF00c980)
val mediumPriorityColor = Color(0xFFFFC114)
val highPriorityColor = Color(0XFFFF4646)
val nonePriorityColor = Color(0xFFFFFFFF)

val taskItemBackgroundColor
@Composable
get() = if (!isSystemInDarkTheme()) Color.White else Color.DarkGray

val Color.topAppBarContentColor:Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Red else Color.Red
