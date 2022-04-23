package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.trivia_home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.components.QuestionsItem
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.trivia_home.TriviaScreenViewModel

@Composable
fun TriviaScreen(viewModel: TriviaScreenViewModel = hiltViewModel()) {
    QuestionsItem()
}