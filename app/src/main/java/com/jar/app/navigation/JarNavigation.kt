package com.jar.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.Modifier
import com.jar.app.ui.home.JarViewModel
import com.jar.app.ui.splash.SplashScreen

@Composable
fun JarNavigation(viewModel: JarViewModel = hiltViewModel()){

    val navController : NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = JarScreens.SplashScreen.name
    ) {
        composable(route = JarScreens.SplashScreen.name) {
            SplashScreen(navController)
        }


    }


}