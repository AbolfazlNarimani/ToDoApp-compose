package com.abe.composetodo.ui.util.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abe.composetodo.R


@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(0.6F),
            painter = painterResource(id = R.drawable.sadface),
            contentDescription = stringResource(R.string.nodata),

            )
        Text(
            modifier = Modifier.alpha(0.6F),
            text = stringResource(R.string.no_task_found),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent()
}