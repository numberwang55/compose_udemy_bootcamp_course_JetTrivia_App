package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.domain.repository

import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.DataOrException
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.dto.QuestionDtoItem

interface TriviaRepository {

    suspend fun getQuestions(): DataOrException<ArrayList<QuestionDtoItem>, Boolean, Exception>

}