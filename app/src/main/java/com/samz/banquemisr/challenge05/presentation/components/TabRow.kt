package com.samz.banquemisr.challenge05.presentation.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.presentation.home.MovieIntent
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme

@Composable
fun TabRow(selectedTabIndex: Int = 0, onTabSelected: (MovieIntent, Int) -> Unit) {
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
                    onTabSelected(
                        when (index) {
                            0 -> MovieIntent.LoadNowPlaying
                            1 -> MovieIntent.LoadPopular
                            else -> MovieIntent.LoadUpcoming
                        },
                        index
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun previewTabs() {
    MoviesAppTheme {
        TabRow(onTabSelected = { _, _ -> })
    }
}