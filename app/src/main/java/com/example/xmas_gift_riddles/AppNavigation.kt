package com.example.xmas_gift_riddles

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "xmas") {
        composable("xmas") {
            Greeting(name = "Marta", onButtonClick =
            { navController.navigate("family-pic") })
        }
        composable("family-pic"){ FamilyImage(onButtonClick = {navController.navigate("family-riddle")})}
        composable("family-riddle"){ FamilyImageRiddle(onButtonClick = {navController.navigate("square-riddle")}) }
        composable("square-riddle"){ SquareRiddle({}) }
//        composable("images") { Finale() }
    }
}