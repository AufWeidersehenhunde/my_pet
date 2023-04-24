package com.example.experimentation.API

import com.squareup.moshi.Json

data class ApiResponse(
    @Json(name = "latest") val latest: List<RepositoryRemoteItemLatest> = listOf()
): java.io.Serializable

data class ApiResponseSale(
    @Json(name = "flash_sale") val flash_sale: List<RepositoryRemoteItemSale> = listOf()
): java.io.Serializable