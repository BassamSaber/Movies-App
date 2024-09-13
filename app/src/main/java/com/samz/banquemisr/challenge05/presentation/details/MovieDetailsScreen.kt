package com.samz.banquemisr.challenge05.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.core.format
import com.samz.banquemisr.challenge05.core.toMovieDuration
import com.samz.banquemisr.challenge05.presentation.DataState
import com.samz.banquemisr.challenge05.presentation.components.ErrorScreen
import com.samz.banquemisr.challenge05.presentation.components.MovieRateView
import com.samz.banquemisr.challenge05.presentation.list.HorizontalMoviesList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    state: DataState<MovieDetailsData>,
    navigate: (movieId: Int) -> Unit = { _ -> },
    popBack: () -> Unit = {}
) {

    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    BackButton(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.TopStart),
                        onClick = popBack
                    )
                    CircularProgressIndicator(
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            } else if (state.error != null || state.data?.movie == null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    BackButton(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.TopStart),
                        onClick = popBack
                    )
                    ErrorScreen(errorMsg = state.error)
                }
            } else {
                val movie = state.data.movie
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())

                ) {
                    ConstraintLayout {
                        val (posterImg, backgroundImg, backBtn) = createRefs()

                        AsyncImage(
                            model = movie.backdropImgUrl,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .constrainAs(backgroundImg) {
                                    top.linkTo(parent.top)
                                }
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(
                                            Brush.verticalGradient(
                                                0.5f to Color.Black.copy(alpha = 0.4f),
                                                1f to Color.Black.copy(alpha = 0.8f)
                                            )
                                        )
                                    }
                                }
                                .fillMaxWidth()

                                .height(300.dp),
                            contentScale = ContentScale.Crop,
                        )
                        AsyncImage(
                            model = movie.posterImgUrl,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .constrainAs(posterImg) {
                                    bottom.linkTo(parent.bottom, margin = 12.dp)
                                    start.linkTo(parent.start, margin = 20.dp)
                                }
                                .width(100.dp)
                                .height(150.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop,
                        )

                        BackButton(
                            modifier = Modifier
                                .constrainAs(backBtn) {
                                    top.linkTo(parent.top, margin = 20.dp)
                                    start.linkTo(parent.start, margin = 20.dp)
                                }
                                .padding(0.dp)
                                .background(
                                    color = Color.Gray.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            onClick = popBack
                        )
                    }
                    FlowRow(
                        modifier = Modifier
                            .padding(vertical = 14.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = movie.title ?: movie.originalTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = Modifier.padding(horizontal = 4.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "(${movie.releaseDate?.format("yyyy") ?: ""})",
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                            modifier = Modifier
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(bottom = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MovieRateView(
                            rate = movie.voteAverage, modifier = Modifier
                                .padding(start = 7.dp, end = 10.dp)
                                .size(40.dp),
                            textSize = 12.sp
                        )
                        Text(
                            text = stringResource(id = R.string.users_score),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (movie.isAnAdult)
                            Icon(
                                painter = painterResource(id = R.drawable.ic_adult),
                                contentDescription = "",
                                Modifier
                                    .size(45.dp)
                                    .padding(horizontal = 10.dp)
                            )
                        Text(text = movie.releaseDate?.format("dd/MM/yyy") ?: "")
                        Text(
                            text = "(${movie.originalLanguage.uppercase()})",
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        VerticalDivider(
                            Modifier
                                .height(14.dp)
                                .padding(horizontal = 14.dp)
                        )
                        Text(
                            text = movie.runtime?.toMovieDuration() ?: "",
                        )
                    }
                    if (!movie.genres.isNullOrEmpty())
                        LazyRow(modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)) {
                            items(movie.genres.size) { index ->
                                val text =
                                    if (index == movie.genres.size - 1)
                                        movie.genres[index].name
                                    else "${movie.genres[index].name}, "
                                Text(text = text, fontSize = 12.sp)
                            }
                        }
                    Text(
                        text = stringResource(id = R.string.overview),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 14.dp)
                    )
                    Text(
                        text = movie.overview ?: "",
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(
                                start = 14.dp,
                                top = 4.dp,
                                bottom = 10.dp,
                                end = 14.dp
                            )
                    )

                    if (state.data.recommendationsList.isNotEmpty())
                        Text(
                            text = stringResource(id = R.string.recommendations),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 14.dp)
                        )
                    if (state.data.recommendationsList.isNotEmpty())
                        HorizontalMoviesList(
                            movies = state.data.recommendationsList,
                            onItemClick = { movieId ->
                                navigate.invoke(movieId)
                            })
                }
            }
        }
    }
}
