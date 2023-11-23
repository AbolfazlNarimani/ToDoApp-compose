package com.abe.composetodo.ui.util.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.abe.composetodo.R
import com.abe.composetodo.components.PriorityItem
import com.abe.composetodo.data.modules.Priority
import com.abe.composetodo.ui.Action
import com.abe.composetodo.ui.theme.TOP_APP_BAR_HEIGHT
import com.abe.composetodo.ui.theme.ELEVATION
import com.abe.composetodo.ui.util.SearchAppBarState
import com.abe.composetodo.ui.util.TrailingIconState
import com.abe.composetodo.viewmodels.SharedViewModel

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onScreenClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                onDeleteAllClicked = {sharedViewModel.action.value = Action.DELETE_ALL}
            )
        }

        else -> {
            SearchAppBar(text = searchTextState,
                onTextChange = { newText ->
                    sharedViewModel.searchTextState.value = newText
                    sharedViewModel.searchDatabase(newText)
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    sharedViewModel.searchDatabase(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onScreenClicked: () -> Unit, onSortClicked: (Priority) -> Unit, onDeleteAllClicked: () -> Unit
) {
    TopAppBar(title = {
        Text(text = stringResource(R.string.tasks))
    },
        // how to access background color in new material version
        //colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background.topAppBarContentColor),

        actions = {
            ListAppBarActions(onScreenClicked, onSortClicked, onDeleteAllClicked)
        })
}

@Composable
fun ListAppBarActions(
    onScreenClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onScreenClicked = onScreenClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(onScreenClicked: () -> Unit) {

    IconButton(onClick = { onScreenClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_tasks)
        )
    }
}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {

    var expended by remember { mutableStateOf(false) }

    IconButton(onClick = { expended = true }) {
        Icon(
            painter = painterResource(id = R.drawable.filter_list),
            contentDescription = stringResource(
                R.string.sort_by_priority
            )
        )
        DropdownMenu(expanded = expended, onDismissRequest = { expended = false }) {
            DropdownMenuItem(text = { PriorityItem(priority = Priority.HIGH) }, onClick = {
                expended = false
                onSortClicked(Priority.HIGH)
            })
            DropdownMenuItem(text = { PriorityItem(priority = Priority.MEDIUM) }, onClick = {
                expended = false
                onSortClicked(Priority.MEDIUM)
            })
            DropdownMenuItem(text = { PriorityItem(priority = Priority.LOW) }, onClick = {
                expended = false
                onSortClicked(Priority.LOW)
            })
            DropdownMenuItem(text = { PriorityItem(priority = Priority.NONE) }, onClick = {
                expended = false
                onSortClicked(Priority.NONE)
            })
        }
    }
}

@Composable
fun DeleteAllAction(onDeleteClicked: () -> Unit) {
    var expended by remember { mutableStateOf(false) }

    IconButton(onClick = { expended = true }) {
        Icon(
            painter = painterResource(id = R.drawable.vertical_drop_down),
            contentDescription = stringResource(
                R.string.deleteall
            )
        )
    }
    DropdownMenu(expanded = expended, onDismissRequest = { expended = false }) {
        DropdownMenuItem(text = { Text(text = stringResource(R.string.deleteall)) },
            onClick = { onDeleteClicked(); expended = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (text: String) -> Unit
) {

    var trailingIconState by remember{ mutableStateOf(TrailingIconState.READY_TO_CLOSE)}

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        shadowElevation = ELEVATION,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(text = stringResource(R.string.search), modifier = Modifier.alpha(0.6F))
            },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { onSearchClicked(text) }, modifier = Modifier.alpha(0.5F)) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.searchicon)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when(trailingIconState){
                        TrailingIconState.READY_TO_DELETE -> {
                            onTextChange("")
                           trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()){
                                onTextChange("")
                            }else{
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(text)
            }),

            )
    }


}

@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(onScreenClicked = {}, onSortClicked = {}, {})
    //SearchAppBar("search",{},{},{})
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    // DefaultListAppBar(onScreenClicked = {}, onSortClicked = {}, {})
    SearchAppBar("", {}, {}, {})
}