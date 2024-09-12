package com.samz.banquemisr.challenge05.presentation.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.presentation.home.MovieIntent
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme

@Composable
fun TabRow(onTabSelected: (MovieIntent) -> Unit) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.now_playing),
        stringResource(R.string.popular),
        stringResource(R.string.upcoming)
    )

    TabRow(
        selectedTabIndex = selectedTabIndex
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    when (index) {
                        0 -> onTabSelected(MovieIntent.LoadNowPlaying)
                        1 -> onTabSelected(MovieIntent.LoadPopular)
                        2 -> onTabSelected(MovieIntent.LoadUpcoming)
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun previewTabs() {
    MoviesAppTheme {
        TabRow(onTabSelected = {})
    }
}