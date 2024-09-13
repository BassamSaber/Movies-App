package com.samz.banquemisr.challenge05.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.core.MoviesType.*
import com.samz.banquemisr.challenge05.core.items
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.presentation.components.EmptyScreen
import com.samz.banquemisr.challenge05.presentation.components.ErrorScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    title: String,
    lazyPagingItems: LazyPagingItems<Movie>,
    navigateToDetails: (movieId: Int) -> Unit = { _ -> },
    popBack: () -> Unit = {}
) {
    val state = lazyPagingItems.loadState

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = title) }, navigationIcon = {
            IconButton(onClick = { popBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back)
                )
            }
        })
    }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.refresh is LoadState.Loading) {
                    CircularProgressIndicator(
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("Loading"),
                    )
                } else if (state.refresh is LoadState.Error) {
                    ErrorScreen(errorMsg = (state.refresh as LoadState.Error).error.message)
                    /*{
                            lazyPagingItems.refresh()
                        }*/
                } else if (lazyPagingItems.itemCount == 0) {
                    EmptyScreen()
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(lazyPagingItems) { movie ->
                            MovieItem(
                                movie = movie,
                                modifier = Modifier
                                    .padding(5.dp),
                                viewDetails = { movieId ->
                                    navigateToDetails.invoke(movieId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}