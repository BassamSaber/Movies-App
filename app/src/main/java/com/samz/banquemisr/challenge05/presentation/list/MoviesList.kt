package com.samz.banquemisr.challenge05.presentation.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samz.banquemisr.challenge05.domain.model.Movie

@Composable
fun HorizontalMoviesList(movies: List<Movie>, onItemClick: (Int) -> Unit) {
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
    }
}