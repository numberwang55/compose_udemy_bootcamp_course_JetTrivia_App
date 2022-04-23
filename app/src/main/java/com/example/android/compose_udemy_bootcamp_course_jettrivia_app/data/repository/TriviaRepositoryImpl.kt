package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.repository

import android.util.Log
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.DataOrException
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.TriviaApi
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.dto.QuestionDtoItem
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.domain.repository.TriviaRepository
import java.lang.Exception
import javax.inject.Inject

class TriviaRepositoryImpl @Inject constructor(
    private val api: TriviaApi
): TriviaRepository {

    private val dataOrException = DataOrException<ArrayList<QuestionDtoItem>, Boolean, Exception>()

    override suspend fun getQuestions(): DataOrException<ArrayList<QuestionDtoItem>, Boolean, Exception> {
        try {
            dataOrException.isLoading = true
            dataOrException.data = api.getQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.isLoading = false
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exception", "getQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }
}