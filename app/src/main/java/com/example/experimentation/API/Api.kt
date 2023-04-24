package com.example.experimentation.API

import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatest(): Response<ApiResponse>

    @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getSale(): Response<ApiResponseSale>
}