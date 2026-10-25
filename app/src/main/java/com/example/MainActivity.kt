package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ui.DashboardScreen
import com.example.ui.NexusDashboardViewModel
import com.example.ui.theme.CyberBg
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val dashboardViewModel: NexusDashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.auth.configureAppCheck(this, intent)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CyberBg
                ) {
                    DashboardScreen(viewModel = dashboardViewModel)
                }
            }
        }
    }
}

