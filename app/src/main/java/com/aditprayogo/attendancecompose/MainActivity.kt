package com.aditprayogo.attendancecompose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aditprayogo.attendancecompose.ui.screens.AttendanceFirebaseScreen
import com.aditprayogo.attendancecompose.ui.theme.AttendanceComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AttendanceComposeTheme {
                // A surface container using the 'background' color from the theme
                AttendanceFirebaseScreen()
            }
        }
    }
}