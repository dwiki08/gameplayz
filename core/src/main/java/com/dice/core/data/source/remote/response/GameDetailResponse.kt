package com.dice.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("rating")
    val rating: Float?,

    @field:SerializedName("rating_top")
    val ratingTop: Float?,

    @field:SerializedName("ratings_count")
    val ratingsCount: Int,

    @field:SerializedName("genres")
    val genres: List<GenresItemResponse>,

    @field:SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatformsItemResponse>,

    @field:SerializedName("released")
    val released: String?,

    @field:SerializedName("background_image")
    val backgroundImage: String?,

    @field:SerializedName("description_raw")
    val descriptionRaw: String?,
)