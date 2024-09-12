package com.samz.banquemisr.challenge05.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun MovieRateView(
    rate: Float,
    modifier: Modifier = Modifier
        .padding(start = 7.dp)
        .size(30.dp),
    textSize: TextUnit = 8.sp,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black)
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        val processColor =
            if (rate > 0.8) Color.Green else if (rate > 0.5) Color.Yellow else Color.Red
        Text(
            text = (rate * 100).roundToInt().toString(),
            color = Color.White,
            fontSize = textSize,
            fontWeight = FontWeight.SemiBold
        )
        CircularProgressIndicator(
            progress = { rate },
            modifier = Modifier.fillMaxSize(),
            color = processColor,
            strokeWidth = 1.5.dp,
            trackColor = processColor.copy(alpha = 0.3f),
        )
    }
}
