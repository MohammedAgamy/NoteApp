package com.example.noteapp.ui.components.pager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavPager() {
    //handel  navController to slid 3 page and next step
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(onFinish = {
                navController.navigate("home") {
                    popUpTo("onboarding") {
                        inclusive = true
                    }
                }
            }
            )

        }
        composable("home") {

        }

    }

}