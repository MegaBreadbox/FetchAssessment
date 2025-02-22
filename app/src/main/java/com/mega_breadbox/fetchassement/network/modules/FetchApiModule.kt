package com.mega_breadbox.fetchassement.network.modules

import com.mega_breadbox.fetchassement.network.FetchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetchApiModule {

    @Provides
    @Singleton
    fun provideFetchApiService(): FetchApiService {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .build()
            .create(FetchApiService::class.java)
    }
}