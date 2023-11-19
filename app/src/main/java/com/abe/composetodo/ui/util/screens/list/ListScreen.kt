package com.abe.composetodo.ui.util.screens.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.abe.composetodo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onFabClicked: (Int) -> Unit
){
    Scaffold(
        content = {},
        floatingActionButton = {
            listFab(navigateToTaskScreen = onFabClicked)
        }
    )
}

@Composable
fun listFab(
    navigateToTaskScreen: (Int) -> Unit
){
    FloatingActionButton(onClick = {
        navigateToTaskScreen(-1)
    }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_button))
    }
}

@Composable
@Preview
private fun ListScreenPreview(){
    ListScreen(onFabClicked = {})
}