package com.dice.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val description: String,
    val releaseDate: String? = "",
    val posterImage: String,
    val platforms: List<Platform>,
    val genres: String,
    val ratingsCount: Int,
    val rating: Float,
    val ratingTop: Float,
    val isFavorite: Boolean,
) : Parcelable {
    enum class Platform {
        PC, MAC_OS, XBOX, PLAYSTATION, NINTENDO, LINUX
    }
}