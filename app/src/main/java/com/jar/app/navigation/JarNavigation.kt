package com.jar.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jar.app.ui.auth.SignInScreen
import com.jar.app.ui.auth.SignUpScreen
import com.jar.app.ui.goldpricetracker.GoldPriceTrackerScreen
import com.jar.app.ui.home.HomeScreen
import com.jar.app.ui.home.HomeScreenViewModel
import com.jar.app.ui.invest.InvestScreen
import com.jar.app.ui.nek.NekScreen
import com.jar.app.ui.profile.ProfileScreen
import com.jar.app.ui.splash.SplashScreen
import com.jar.app.ui.transactions.TransactionsScreen
import com.jar.app.ui.videoplayer.VideoPlayerScreen

@Composable
fun JarNavigation(viewModel: HomeScreenViewModel = hiltViewModel()){

    val navController : NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = JarScreens.SplashScreen.name
    ) {
        composable(route = JarScreens.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(
            route = "${JarScreens.VideoPlayerScreen.name}/{videoId}/{videoTitle}",
            arguments = listOf(
                navArgument("videoId") { type = NavType.StringType },
                navArgument("videoTitle") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            val videoTitle = backStackEntry.arguments?.getString("videoTitle") ?: "Video"
            VideoPlayerScreen(videoId, videoTitle, navController)
        }

        composable(route = JarScreens.SigninScreen.name) {
            SignInScreen(navController)
        }

        composable(route = JarScreens.SignupScreen.name) {
            SignUpScreen(navController)
        }

        composable(route = JarScreens.HomeScreen.name) {
            HomeScreen(navController)
        }

        composable(route = JarScreens.ProfileScreen.name) {
            ProfileScreen(navController)
        }

        composable(route = JarScreens.NekScreen.name) {
            NekScreen(navController)
        }

        composable(route = JarScreens.TransactionScreen.name) {
            TransactionsScreen(navController)
        }
        composable(route = JarScreens.InvestScreen.name) {
            InvestScreen(navController)
        }
        composable(route = JarScreens.GoldPriceTrackerScreen.name) {
            GoldPriceTrackerScreen(navController)
        }

    }
}