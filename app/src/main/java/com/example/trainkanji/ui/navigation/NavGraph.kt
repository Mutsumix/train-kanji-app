package com.example.trainkanji.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trainkanji.data.QuizMode
import com.example.trainkanji.ui.screens.*

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Settings : Screen("settings")
    object Report : Screen("report")
    object Quiz : Screen("quiz/{mode}") {
        fun createRoute(mode: QuizMode) = "quiz/${mode.name}"
    }
    object Result : Screen("result")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        // スタート画面
        composable(Screen.Start.route) {
            StartScreen(
                onStartQuiz = { mode ->
                    navController.navigate(Screen.Quiz.createRoute(mode))
                },
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // 設定画面
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onOpenReport = {
                    navController.navigate(Screen.Report.route)
                }
            )
        }

        // 学習レポート画面
        composable(Screen.Report.route) {
            ReportScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // クイズ画面
        composable(
            route = Screen.Quiz.route,
            arguments = listOf(navArgument("mode") { type = NavType.StringType })
        ) { backStackEntry ->
            val mode = QuizMode.valueOf(
                backStackEntry.arguments?.getString("mode") ?: "NORMAL"
            )
            QuizScreen(
                mode = mode,
                onFinish = {
                    navController.navigate(Screen.Result.route) {
                        // クイズ画面をバックスタックから削除
                        popUpTo(Screen.Quiz.route) { inclusive = true }
                    }
                },
                onExit = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        // 結果画面
        composable(Screen.Result.route) {
            ResultScreen(
                onRetry = {
                    // 同じモードで再挑戦（前のクイズ画面の設定を引き継ぐ）
                    navController.popBackStack()
                    navController.popBackStack()
                },
                onBackToStart = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
