package com.dice.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ParentPlatformsItemResponse(
    @field:SerializedName("platform")
    val platform: Platform
) {
    data class Platform(

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("slug")
        val slug: String
    )
}