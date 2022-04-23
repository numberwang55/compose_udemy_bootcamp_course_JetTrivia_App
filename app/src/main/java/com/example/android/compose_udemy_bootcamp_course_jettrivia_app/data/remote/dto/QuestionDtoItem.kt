package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.dto

data class QuestionDtoItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)