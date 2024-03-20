package com.dheeraj.advaitapplication.api

import com.dheeraj.advaitapplication.data.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = "weAaIWU18zdWAqxLIwcu2dEQZXb38D5Pj1YUUJID8rQ"
//        const val CLIENT_ID = "7bf0FdjWjzEGS4tIZGEZTz9mN6B4aJNL5tRcldGpr5w"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}