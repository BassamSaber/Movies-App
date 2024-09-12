package com.samz.banquemisr.challenge05.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.presentation.DataState
import com.samz.banquemisr.challenge05.presentation.components.EmptyScreen
import com.samz.banquemisr.challenge05.presentation.components.ErrorScreen
import com.samz.banquemisr.challenge05.presentation.components.TabRow
import com.samz.banquemisr.challenge05.presentation.components.carsoule.HomeCarousel
import com.samz.banquemisr.challenge05.presentation.list.HorizontalMoviesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: DataState<HomeData>,
    onMoviesEvent: (MovieIntent) -> Unit,
    navigateToDetails: (movieId: Int) -> Unit = { _ -> },
) {

    LaunchedEffect(MovieIntent.LoadNowPlaying) {
        onMoviesEvent(MovieIntent.LoadNowPlaying)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = rememberAsyncImagePainter(R.drawable.ic_logo),
                            contentDescription = "Image",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(40.dp),
                            tint = null
                        )
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontWeight = FontWeight.Bold
                        )
                    }
                })

                TabRow(
                    onTabSelected = { intent ->
                        onMoviesEvent(intent)
                    }
                )
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                } else if (state.error != null) {
                    ErrorScreen()
                } else if (state.isEmpty) {
                    EmptyScreen()
                } else {

                    Column {
                        HomeCarousel(state.data?.banners ?: emptyList())
                        HorizontalMoviesList(movies = state.data?.movies ?: emptyList(),
                            onItemClick = { movieId ->
                                navigateToDetails.invoke(movieId)
                            })
                    }
                }
            }
        }
    }
}