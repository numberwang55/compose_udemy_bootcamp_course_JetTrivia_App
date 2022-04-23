package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote

import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.dto.QuestionDto
import retrofit2.http.GET

interface TriviaApi {

    @GET("world.json")
    suspend fun getQuestions(): QuestionDto

}