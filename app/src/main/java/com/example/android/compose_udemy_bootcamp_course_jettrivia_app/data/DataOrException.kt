package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data

data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var isLoading: Boolean? = null,
    var e: E? = null
)
