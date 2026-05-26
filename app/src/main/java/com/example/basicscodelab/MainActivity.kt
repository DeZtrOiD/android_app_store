package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.basicscodelab.data.repository.AssetAppRepository
import com.example.basicscodelab.ui.navigation.AppNavHost
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme
import com.example.basicscodelab.ui.viewmodel.AppViewModel
import com.example.basicscodelab.ui.viewmodel.AppViewModelFactory
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        AssetAppRepository(applicationContext)
    }

    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                AppNavHost(viewModel = viewModel)
            }
        }
    }
}
