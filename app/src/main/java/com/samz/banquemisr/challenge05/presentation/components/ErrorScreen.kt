package com.samz.banquemisr.challenge05.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Warning
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
fun ErrorScreen(errorMsg: String? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Sharp.Warning,
            contentDescription = stringResource(R.string.error_screen_image),
            tint = Color.LightGray,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = errorMsg ?: stringResource(R.string.something_went_wrong),
            color = Color.LightGray,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
    }
}


@Preview
@Composable
fun previewErrorScreen() {
    MoviesAppTheme {
        Scaffold(
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    ErrorScreen()
                }
            }
        )
    }
}