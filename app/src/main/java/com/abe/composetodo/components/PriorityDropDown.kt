package com.abe.composetodo.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abe.composetodo.R
import com.abe.composetodo.ui.util.Constants.PRIORITY_DROP_DOWN_HEIGHT
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.ui.theme.PRIORITY_INDICATOR_SIZE
import kotlin.math.exp

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {

    var expended by remember {
        mutableStateOf(false)
    }
    val angle by animateFloatAsState(targetValue = if (expended) 180F else 0F, label = "PriorityDropDownMenuAnimation")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expended = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = DefaultAlpha),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1F)
        ) {
            drawCircle(color = priority.color)
        }
        Text(modifier = Modifier.weight(8F), text = priority.name)
        IconButton(modifier = Modifier
            .alpha(0.7F)
            .rotate(degrees = angle)
            .weight(1.5F), onClick = { expended = true }) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.dropdown_arrow)
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(fraction = 0.94F),
            expanded = expended,
            onDismissRequest = { expended = false },
        ) {

            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.LOW) },
                onClick = { expended = false })
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.MEDIUM) },
                onClick = { expended = false })
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.HIGH) },
                onClick = { expended = false })
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.NONE) },
                onClick = { expended = false })
        }
    }


}


@Composable
@Preview
fun PriorityDropDownPreview() {
    PriorityDropDown(priority = Priority.MEDIUM, onPrioritySelected = {})
}