package com.samz.banquemisr.challenge05.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme

@Composable
fun EmptyScreen(emptyTxtMsg: String = stringResource(R.string.no_result_has_been_found)) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Sharp.List,
            contentDescription = stringResource(R.string.empty_data_image),
            tint = Color.LightGray,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = emptyTxtMsg,
            color = Color.LightGray,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun previewEmptyScreen() {
    MoviesAppTheme {
        Scaffold(
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    EmptyScreen()
                }
            }
        )
    }
}