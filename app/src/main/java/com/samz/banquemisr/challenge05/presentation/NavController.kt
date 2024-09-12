package com.samz.banquemisr.challenge05.presentation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samz.banquemisr.challenge05.presentation.home.HomeScreen
import com.samz.banquemisr.challenge05.presentation.home.HomeViewModel
import com.samz.banquemisr.challenge05.presentation.home.MainViewModel
import com.samz.banquemisr.challenge05.presentation.theme.MoviesAppTheme

sealed class Screen(val name: String) {
    object HomeScreen : Screen("HomeScreen")
    object MovieDetailsScreen : Screen("MovieDetailsScreen")
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
        })
}

@Composable
fun composeMovieDetails(navController: NavController, arguments: Bundle?) {

}