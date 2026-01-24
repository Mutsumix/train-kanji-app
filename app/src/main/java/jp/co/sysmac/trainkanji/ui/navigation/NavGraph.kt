package jp.co.sysmac.trainkanji.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.co.sysmac.trainkanji.data.QuizMode
import jp.co.sysmac.trainkanji.ui.screens.*

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Settings : Screen("settings")
    object Report : Screen("report")
    object Quiz : Screen("quiz/{mode}") {
        fun createRoute(mode: QuizMode) = "quiz/${mode.name}"
    }
    object Result : Screen("result/{score}/{total}/{mode}") {
        fun createRoute(score: Int, total: Int, mode: QuizMode) = "result/$score/$total/${mode.name}"
    }
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
                onFinish = { score, total, quizMode ->
                    navController.navigate(Screen.Result.createRoute(score, total, quizMode)) {
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
        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("mode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            val mode = QuizMode.valueOf(
                backStackEntry.arguments?.getString("mode") ?: "NORMAL"
            )
            ResultScreen(
                score = score,
                total = total,
                onRetry = {
                    // 同じモードで再挑戦
                    navController.navigate(Screen.Quiz.createRoute(mode)) {
                        popUpTo(Screen.Start.route)
                    }
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
