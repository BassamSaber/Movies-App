package com.samz.banquemisr.challenge05.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.core.format
import com.samz.banquemisr.challenge05.core.toDate
import com.samz.banquemisr.challenge05.data.remote.model.MovieGenreDto
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.presentation.components.MovieRateView
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme


@Composable
fun MovieItem(
    movie: Movie, modifier: Modifier = Modifier
        .padding(horizontal = 10.dp)
        .width(150.dp)
        .padding(10.dp), viewDetails: (movieId: Int) -> Unit = { _ -> }
) {
    Column(
        modifier = modifier
            .clickable { viewDetails.invoke(movie.id) },
    ) {
        ConstraintLayout {
            val (image, rate, title, date) = createRefs()

            AsyncImage(
//            SubcomposeAsyncImage(
                model = movie.posterImgUrl,
//                loading = {
//                    Box {
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                    }
//                },
                error = painterResource(R.drawable.img_placeholder),
//                placeholder = painterResource(R.drawable.img_placeholder),
                contentDescription = movie.title,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            MovieRateView(rate = movie.voteAverage, modifier = Modifier
                .constrainAs(rate) {
                    top.linkTo(image.bottom, margin = (12.dp * -1))
                }
                .padding(start = 7.dp)
                .size(30.dp))
            Text(
                text = movie.title ?: movie.originalTitle,
                maxLines = 2,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(rate.bottom, margin = 4.dp)
                    }
                    .padding(start = 10.dp)
            )
            Text(
                text = movie.releaseDate?.format() ?: "",
                fontSize = 12.sp,
                modifier = Modifier
                    .constrainAs(date) {
                        top.linkTo(title.bottom, margin = 2.dp)
                    }
                    .padding(start = 10.dp)
            )

        }

    }
}


@Preview(showBackground = true)
@Composable
fun previewMovieItem() {
    MoviesAppTheme {
        MovieItem(
            movie = Movie(
                id = 1,
                title = "Trap",
                originalTitle = "Trap",
                originalLanguage = "en",
                posterImgUrl = "https://image.tmdb.org/t/p/original/jwoaKYVqPgYemFpaANL941EF94R.jpg",
                backdropImgUrl = "https://image.tmdb.org/t/p/original/p5kpFS0P3lIwzwzHBOULQovNWyj.jpg",
                isAnAdult = false,
                genres = listOf(
                    MovieGenreDto(1, "Horror"),
                    MovieGenreDto(2, "Action"),
                ),
                overview = "A father and teen daughter attend a pop concert, where they realize they're at the center of a dark and sinister event.",
                popularity = 790.927,
                releaseDate = "2024-08-23".toDate(),
                voteAverage = 0.653f,
                voteCount = 968,
                runtime = null
            )
        )
    }
}