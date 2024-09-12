package com.samz.banquemisr.challenge05.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.domain.model.Movie

@Composable
fun HorizontalMoviesList(
    movies: List<Movie>,
    onItemClick: (Int) -> Unit,
    onViewAllClick: (() -> Unit)? = null
) {
    LazyRow(contentPadding = PaddingValues(10.dp)) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(150.dp),
                viewDetails = onItemClick
            )
        }
        if (onViewAllClick != null)
            item {
                Text(
                    text = stringResource(R.string.view_all),
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .background(
                            Color.Black.copy(alpha = 0.01f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .clickable {
                            onViewAllClick.invoke()
                        },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
    }
}