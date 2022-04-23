package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.presentation.trivia_home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.DataOrException
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.dto.QuestionDtoItem
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.domain.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriviaScreenViewModel @Inject constructor(
    private val triviaRepository: TriviaRepository
): ViewModel() {

    var data: MutableState<DataOrException<ArrayList<QuestionDtoItem>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getQuestions()
    }

    private fun getQuestions() {
        viewModelScope.launch {
            data.value.isLoading = true
            data.value = triviaRepository.getQuestions()
            if (data.value.data.toString().isNotEmpty()) {
                data.value.isLoading = false
            }
        }
    }

    fun getQuestionCount(): Int {
        return data.value.data?.size ?: 0
//        return data.value.data?.toMutableList()?.size ?: 0
    }

}