package com.dice.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(

    @field:SerializedName("next")
    val nextUrl: String,

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("user_platforms")
    val userPlatforms: Boolean,

    @field:SerializedName("results")
    val results: List<GameDetailResponse>
)