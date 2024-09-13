package com.samz.banquemisr.challenge05.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
                IconButton(modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 70.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.2f),
                        shape = CircleShape
                    ), onClick = { onViewAllClick() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = stringResource(R.string.back)
                    )
                }
                /* Text(
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
                 )*/
            }
    }
}