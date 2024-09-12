package com.samz.banquemisr.challenge05.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samz.banquemisr.challenge05.R


@Composable
fun BackButton(modifier: Modifier, onClick: () -> Unit = {}) {
    IconButton(
        modifier = modifier
            .padding(0.dp)
            .background(
                color = Color.Gray.copy(alpha = 0.7f),
                shape = RoundedCornerShape(4.dp)
            ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            modifier = Modifier.size(20.dp),
            contentDescription = "Back button"
        )
    }
}