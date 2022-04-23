package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.trivia_home.TriviaScreen
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.ui.theme.Compose_udemy_bootcamp_course_JetTrivia_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                TriviaScreen()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Compose_udemy_bootcamp_course_JetTrivia_AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}