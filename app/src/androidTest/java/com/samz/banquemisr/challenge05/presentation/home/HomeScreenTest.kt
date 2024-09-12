package com.samz.banquemisr.challenge05.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samz.banquemisr.challenge05.helper.expectedHomeData
import com.samz.banquemisr.challenge05.presentation.DataState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// MoviesScreenTest.kt

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var fakeViewModel: HomeViewModel
    private val fakeThemeViewModel: MainViewModel = mockk()

    @Before
    fun setup() {
        fakeViewModel = mockk(relaxed = true)
    }


    @Composable
    fun homeContent() {
        HomeScreen(
            appState = fakeThemeViewModel.stateApp,
            onMainEvent = { event -> fakeThemeViewModel.onEvent(event) },
            state = fakeViewModel.state.value,
            onMoviesEvent = { event -> fakeViewModel.sendEvent(event) }
        )
    }


    @Test
    fun movieScreen_displaysLoadingState_correctly() {
        // Mockk ViewModel with loading state
        coEvery { fakeViewModel.state } returns
                MutableStateFlow(DataState(isLoading = true))

        coEvery { fakeThemeViewModel.stateApp } returns MainState()


        // Set the content of the Compose UI
        composeTestRule.setContent { homeContent() }

        verify { fakeViewModel.sendEvent(MovieIntent.LoadNowPlaying) }

        // Check that the loading indicator is shown
        composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
    }

    @Test
    fun movieScreen_displaysNowPlayingMovies_correctly() {
        // Create a fake ViewModel with mockk data

        coEvery { fakeViewModel.state } returns
                MutableStateFlow(DataState(isLoading = false, data = expectedHomeData))

        coEvery { fakeThemeViewModel.stateApp } returns MainState()


        // Set the content of the Compose UI
        composeTestRule.setContent { homeContent() }

        verify { fakeViewModel.sendEvent(MovieIntent.LoadNowPlaying) }

        // Check that the tabs are displayed correctly
        composeTestRule.onNodeWithText("Now Playing").assertIsDisplayed()
        composeTestRule.onNodeWithText("Popular").assertIsDisplayed()
        composeTestRule.onNodeWithText("Upcoming").assertIsDisplayed()

        // Check that the movies are displayed correctly
        expectedHomeData.movies.firstOrNull()?.apply {
            composeTestRule.onNodeWithText(
                title ?: originalTitle
            ).assertIsDisplayed()
        }
    }

    @Test
    fun movieScreen_displaysErrorState_correctly() {
        // Mockk ViewModel with error state
        coEvery { fakeViewModel.state } returns
                MutableStateFlow(DataState(isLoading = false, error = "An error occurred"))

        coEvery { fakeThemeViewModel.stateApp } returns MainState()


        // Set the content of the Compose UI
        composeTestRule.setContent { homeContent() }

        verify { fakeViewModel.sendEvent(MovieIntent.LoadNowPlaying) }

        // Check that the error message is displayed
        composeTestRule.onNodeWithText("An error occurred").assertIsDisplayed()
    }

    @Test
    fun movieItem_clickTriggersCorrectIntent() {
        // Mockk ViewModel with error state
        coEvery { fakeViewModel.state } returns
                MutableStateFlow(DataState(isLoading = false, data = expectedHomeData))

        coEvery { fakeThemeViewModel.stateApp } returns MainState()

        // Set the content of the Compose UI
        composeTestRule.setContent { homeContent() }

        verify { fakeViewModel.sendEvent(MovieIntent.LoadNowPlaying) }


        expectedHomeData.movies.firstOrNull()?.apply {
            composeTestRule.onNodeWithText(
                title ?: originalTitle
            ).performClick()
        }
    }
}