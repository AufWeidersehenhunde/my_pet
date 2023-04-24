package com.example.experimentation.API

import com.squareup.moshi.Json

data class RepositoryRemoteItemSale(
    @Json(name ="category") val category: String?,
    @Json(name ="name") val name: String?,
    @Json(name ="price") val price: Double?,
    @Json(name = "discount") val discount: Int?,
    @Json(name ="image_url") val image_url: String?,
): java.io.Serializable