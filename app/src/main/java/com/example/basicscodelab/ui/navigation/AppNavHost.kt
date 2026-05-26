package com.example.basicscodelab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.basicscodelab.data.preferences.OnboardingPreferences
import com.example.basicscodelab.ui.screens.AppDetailsScreen
import com.example.basicscodelab.ui.screens.CategoriesScreen
import com.example.basicscodelab.ui.screens.OnboardingScreen
import com.example.basicscodelab.ui.screens.ScreenshotViewerScreen
import com.example.basicscodelab.ui.screens.StoreScreen
import com.example.basicscodelab.ui.viewmodel.AppViewModel

@Composable
fun AppNavHost(
    viewModel: AppViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val startDestination = remember {
        if (OnboardingPreferences.isOnboardingCompleted(context)) {
            Routes.Store
        } else {
            Routes.Onboarding
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Onboarding) {
            OnboardingScreen(
                onContinue = {
                    OnboardingPreferences.setOnboardingCompleted(context)
                    navController.navigate(Routes.Store) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Routes.StorePattern,
            arguments = listOf(
                navArgument(Routes.CategoryArg) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString(Routes.CategoryArg).orEmpty()

            StoreScreen(
                apps = uiState.apps,
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                categoryName = categoryName,
                onOpenApp = { appId ->
                    navController.navigate(Routes.details(appId))
                },
                onOpenCategories = {
                    navController.navigate(Routes.Categories)
                },
                onRetry = { viewModel.loadApps() },
                onBack = if (categoryName.isNotBlank()) {
                    {
                        navController.popBackStack()
                        Unit
                    }
                } else {
                    null
                }
            )
        }

        composable(Routes.Categories) {
            CategoriesScreen(
                apps = uiState.apps,
                isLoading = uiState.isLoading,
                onCategoryClick = { category ->
                    navController.navigate(Routes.store(category.name))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.DetailsPattern,
            arguments = listOf(
                navArgument(Routes.AppIdArg) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt(Routes.AppIdArg) ?: -1
            val app = viewModel.appById(appId)

            if (app != null) {
                AppDetailsScreen(
                    app = app,
                    onBack = { navController.popBackStack() },
                    onOpenScreenshot = { index ->
                        navController.navigate(Routes.screenshots(app.id, index))
                    }
                )
            }
        }

        composable(
            route = Routes.ScreenshotsPattern,
            arguments = listOf(
                navArgument(Routes.AppIdArg) { type = NavType.IntType },
                navArgument(Routes.StartIndexArg) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt(Routes.AppIdArg) ?: -1
            val startIndex = backStackEntry.arguments?.getInt(Routes.StartIndexArg) ?: 0
            val app = viewModel.appById(appId)

            if (app != null) {
                ScreenshotViewerScreen(
                    app = app,
                    startIndex = startIndex,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
