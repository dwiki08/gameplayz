package com.dice.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_game")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "game_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "poster_image")
    val posterImage: String,

    @ColumnInfo(name = "platforms")
    val platforms: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "rating_count")
    val ratingsCount: Int,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "rating_top")
    val ratingTop: Float,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)