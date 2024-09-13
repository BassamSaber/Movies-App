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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.presentation.DataState
import com.samz.banquemisr.challenge05.presentation.components.EmptyScreen
import com.samz.banquemisr.challenge05.presentation.components.ErrorScreen
import com.samz.banquemisr.challenge05.presentation.components.SelectThemeDialog
import com.samz.banquemisr.challenge05.presentation.components.TabRow
import com.samz.banquemisr.challenge05.presentation.components.carsoule.HomeCarousel
import com.samz.banquemisr.challenge05.presentation.list.HorizontalMoviesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appState: MainState,
    onMainEvent: (MainEvent) -> Unit,
    state: DataState<HomeData>,
    onMoviesEvent: (MovieIntent) -> Unit,
    navigateToDetails: (movieId: Int) -> Unit = { _ -> },
    navigateMoviesList: (movieType: Int) -> Unit = { _ -> },
) {
    if (appState.openDialog) {
        SelectThemeDialog(stateApp = appState, onEvent = onMainEvent, setShowDialog = {
            onMainEvent(MainEvent.OpenDialog(it))
        }, returnValue = {})
    }

    LaunchedEffect(Unit) {
        if (!state.initialized)
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
                }, actions = {
                    IconButton(onClick = {
                        onMainEvent(
                            MainEvent.OpenDialog(true)
                        )
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_theme_mode),
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Theme change mode"
                        )
                    }
                })

                TabRow(
                    selectedTabIndex = when (state.data?.moviesType) {
                        MoviesType.NowPlaying -> 0
                        MoviesType.Popular -> 1
                        MoviesType.Upcoming -> 2
                        else -> 0
                    },
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
                            .testTag("Loading"),
                    )
                } else if (state.error != null) {
                    ErrorScreen(errorMsg = state.error)
                } else if (state.isEmpty) {
                    EmptyScreen()
                } else {

                    Column {
                        HomeCarousel(state.data?.banners ?: emptyList())
                        HorizontalMoviesList(
                            movies = state.data?.movies ?: emptyList(),
                            onItemClick = { movieId ->
                                navigateToDetails.invoke(movieId)
                            },
                            onViewAllClick = {
                                state.data?.moviesType?.ordinal?.let {
                                    navigateMoviesList(it)
                                }
                            })
                    }
                }
            }
        }
    }
}