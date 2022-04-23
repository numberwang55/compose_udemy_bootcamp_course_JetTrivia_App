package com.example.android.compose_udemy_bootcamp_course_jettrivia_app.di

import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.remote.TriviaApi
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.data.repository.TriviaRepositoryImpl
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.domain.repository.TriviaRepository
import com.example.android.compose_udemy_bootcamp_course_jettrivia_app.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTriviaApi(): TriviaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TriviaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTriviaRepository(triviaApi: TriviaApi): TriviaRepository {
        return TriviaRepositoryImpl(triviaApi)
    }

}