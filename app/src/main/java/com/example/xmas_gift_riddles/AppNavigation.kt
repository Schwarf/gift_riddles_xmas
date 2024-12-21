package com.example.xmas_gift_riddles

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "xmas") {
        composable("xmas") { Greeting(name = "Marta")  }
//        composable("images") { Finale() }
    }
}