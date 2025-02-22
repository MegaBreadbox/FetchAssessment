package com.mega_breadbox.fetchassement.network

import com.mega_breadbox.fetchassement.network.model.FetchData
import retrofit2.http.GET

interface FetchApiService {

    @GET("hiring.json")
    suspend fun getData(): List<FetchData>

}