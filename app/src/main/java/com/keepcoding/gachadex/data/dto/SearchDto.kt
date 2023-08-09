package com.keepcoding.gachadex.data.dto

import com.squareup.moshi.Json

data class SearchDto (
    @Json(name = "name") val name: String ="",
    @Json(name = "id") val id: String = ""
)