package com.samz.banquemisr.challenge05.presentation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samz.banquemisr.challenge05.R
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.presentation.details.MovieDetailsScreen
import com.samz.banquemisr.challenge05.presentation.details.MovieDetailsViewModel
import com.samz.banquemisr.challenge05.presentation.home.HomeScreen
import com.samz.banquemisr.challenge05.presentation.home.HomeViewModel
import com.samz.banquemisr.challenge05.presentation.home.MainViewModel
import com.samz.banquemisr.challenge05.presentation.list.MoviesListScreen
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme

sealed class Screen(val name: String) {
    object HomeScreen : Screen("HomeScreen")
    object MovieDetailsScreen : Screen("MovieDetailsScreen")
    object MoviesListScreen : Screen("MoviesListScreen")
}

@Composable
fun NavController() {
    val navController = rememberNavController()
    val themeChangeViewModel: MainViewModel = hiltViewModel()

    MoviesAppTheme(appTheme = themeChangeViewModel.stateApp.theme) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.name
        ) {
            composable(
                route = Screen.HomeScreen.name,
            ) {
                composeHomeScreen(
                    themeChangeViewModel = themeChangeViewModel,
                    navController = navController
                )
            }
            composable(
                route = "${Screen.MovieDetailsScreen.name}/{movieId}",
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType },
                ),
            ) {
                composeMovieDetails(navController = navController, arguments = it.arguments)
            }
            composable(
                route = "${Screen.MoviesListScreen.name}/{moviesType}",
                arguments = listOf(
                    navArgument("moviesType") { type = NavType.IntType },
                ),
            ) {
                composeMoviesList(navController = navController, arguments = it.arguments)
            }
        }
    }
}

@Composable
fun composeHomeScreen(themeChangeViewModel: MainViewModel, navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()

    HomeScreen(
        appState = themeChangeViewModel.stateApp,
        onMainEvent = { event -> themeChangeViewModel.onEvent(event) },
        state = viewModel.state.collectAsState().value,
        onMoviesEvent = { event -> viewModel.sendEvent(event) },
        navigateToDetails = { movieId ->
            navController.navigate("${Screen.MovieDetailsScreen.name}/$movieId")
        },
        navigateMoviesList = { moviesType ->
            navController.navigate("${Screen.MoviesListScreen.name}/$moviesType")
        })
}

@Composable
fun composeMovieDetails(navController: NavController, arguments: Bundle?) {
    val movieId = arguments?.getInt("movieId") ?: 0
    val viewModel: MovieDetailsViewModel = hiltViewModel()

    LaunchedEffect(movieId) {
        viewModel.loadData(movieId = movieId)
    }
    MovieDetailsScreen(
        viewModel.state.collectAsState().value,
        navigate = { newMovieId ->
            navController.navigate("${Screen.MovieDetailsScreen.name}/$newMovieId")
        },
        popBack = {
            navController.popBackStack()
        }
    )
}

@Composable
fun composeMoviesList(navController: NavController, arguments: Bundle?) {
    val moviesTypeIndex = arguments?.getInt("moviesType") ?: 0
    val moviesType = MoviesType.values()[moviesTypeIndex]

    val viewModel: HomeViewModel = hiltViewModel()

    MoviesListScreen(
        moviesType = moviesType,
        title = stringResource(
            id = when (moviesType) {
                MoviesType.Popular -> R.string.popular
                MoviesType.Upcoming -> R.string.upcoming
                MoviesType.NowPlaying -> R.string.now_playing
            }
        ),
        state = viewModel.state.collectAsState().value,
        sendEvent = { event -> viewModel.sendEvent(event) },
        navigateToDetails = { newMovieId ->
            navController.navigate("${Screen.MovieDetailsScreen.name}/$newMovieId")
        },
        popBack = {
            navController.popBackStack()
        }
    )
}