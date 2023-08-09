package com.keepcoding.gachadex.data.dto.pokemon

import com.squareup.moshi.Json

data class PictureDTO(
    @Json(name="other") val artworks: ArtworkDTO
)

data class ArtworkDTO(
    @Json(name = "official-artwork") val officialArtwork: OfficialArtworkDTO
)

data class OfficialArtworkDTO(
    @Json(name = "front_default") val url: String
)