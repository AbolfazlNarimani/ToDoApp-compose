  package com.abe.composetodo.ui.util.screens.task

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abe.composetodo.R
import com.abe.composetodo.components.PriorityDropDown
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.ui.theme.LARGE_PADDING
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection

  @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    date: String,
    onDateSelected: (String) -> Unit,
    time: String,
    onTimeSelected: (String) -> Unit
) {

    val calenderState = rememberUseCaseState()
    CalendarDialog(state = calenderState, config = CalendarConfig(monthSelection = true, yearSelection = true) ,selection = CalendarSelection.Date{
        dateFromCalender ->
        val formattedDate = dateFromCalender.toString()
        onTimeSelected(formattedDate)
        Log.d("Selected", formattedDate)
        calenderState.invokeReset()
    })

    val clockState = rememberUseCaseState()
    ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes{hours, minutes ->
        val timeFromClock = "$hours:$minutes"
        Log.d("SELECTED TIME", "$hours:$minutes")
        onDateSelected(timeFromClock)
        clockState.invokeReset()
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = LARGE_PADDING)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )

        Row {
            IconButton(modifier = Modifier
                .weight(1F)
                .padding(top = 1.dp), onClick = {clockState.show()}) {
                Icon(
                    painter = painterResource(id = R.drawable.time_picker),
                    contentDescription = stringResource(
                        R.string.timepicker
                    )
                )
            }

            Row(Modifier.padding(end = 50.dp, start = 50.dp)) {
                Text(modifier = Modifier
                    //.weight(1F)
                    .padding(top = 16.dp, end = 15.dp),text = time)


                Text(modifier = Modifier
                    //.weight(1F)
                    .padding(top = 16.dp, start = 15.dp),text = date)
            }




            IconButton(modifier = Modifier.weight(1F), onClick = { calenderState.show() }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(R.string.datepicker)
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.bodyMedium
        )

    }

}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        title = "Blyad",
        onTitleChange = {},
        description = "CykaBlyad",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {},
        date = "2023",
        onDateSelected = {},
        time = "12:23",
        onTimeSelected = {}
    )
}