package com.dice.core.utils

import com.dice.core.data.source.local.entity.GameEntity
import com.dice.core.data.source.remote.response.GameDetailResponse
import com.dice.core.data.source.remote.response.GenresItemResponse
import com.dice.core.data.source.remote.response.ParentPlatformsItemResponse
import com.dice.core.domain.model.Game

@Suppress("unused")
object DataMapper {
    fun GameDetailResponse.toModel(): Game {
        return Game(
            this.id,
            this.name,
            this.descriptionRaw ?: "",
            this.released,
            this.backgroundImage ?: "",
            this.parentPlatforms.toModel(),
            genresDtoToString(this.genres),
            this.ratingsCount,
            this.rating ?: 0f,
            this.ratingTop ?: 0f,
            false
        )
    }

    fun GameEntity.toModel(): Game {
        return Game(
            this.id,
            this.name,
            this.description,
            this.releaseDate,
            this.posterImage,
            platformEntityToDomain(this.platforms),
            this.genres,
            this.ratingsCount,
            this.rating,
            this.ratingTop,
            this.isFavorite
        )
    }

    fun Game.toEntity(): GameEntity {
        return GameEntity(
            this.id,
            this.name,
            this.description,
            this.releaseDate ?: "",
            this.posterImage,
            platformDomainToEntity(this.platforms),
            this.genres,
            this.ratingsCount,
            this.rating,
            this.ratingTop,
            this.isFavorite
        )
    }

    @JvmName("responseToModel")
    fun List<GameDetailResponse>.toModel(): List<Game> {
        return this.map { it.toModel() }
    }

    @JvmName("entityToModel")
    fun List<GameEntity>.toModel(): List<Game> {
        return this.map { it.toModel() }
    }

    @JvmName("modelToEntity")
    fun List<Game>.toEntity(): List<GameEntity> {
        return this.map { it.toEntity() }
    }

    @JvmName("responsePlatformToModel")
    fun List<ParentPlatformsItemResponse>.toModel(): MutableList<Game.Platform> {
        val platform = mutableListOf<Game.Platform>()
        this.forEach { p ->
            when (p.platform.name.uppercase()) {
                "PC" -> platform.add(Game.Platform.PC)
                "PLAYSTATION" -> platform.add(Game.Platform.PLAYSTATION)
                "XBOX" -> platform.add(Game.Platform.XBOX)
                "LINUX" -> platform.add(Game.Platform.LINUX)
                "MACOS" -> platform.add(Game.Platform.MAC_OS)
            }
        }
        return platform
    }

    private fun platformEntityToDomain(data: String): MutableList<Game.Platform> {
        val platform = mutableListOf<Game.Platform>()
        data.split(",").forEach {
            when (it.uppercase()) {
                "PC" -> platform.add(Game.Platform.PC)
                "PLAYSTATION" -> platform.add(Game.Platform.PLAYSTATION)
                "XBOX" -> platform.add(Game.Platform.XBOX)
                "LINUX" -> platform.add(Game.Platform.LINUX)
                "MACOS" -> platform.add(Game.Platform.MAC_OS)
            }
        }
        return platform
    }

    private fun platformDomainToEntity(data: List<Game.Platform>): String {
        val result = StringBuilder().append("")
        for (i in data.indices) {
            if (i < data.size - 1) {
                result.append("${data[i].name},")
            } else {
                result.append(data[i].name)
            }
        }
        return result.toString()
    }

    private fun genresDtoToString(data: List<GenresItemResponse>): String {
        val result = StringBuilder().append("")
        for (i in data.indices) {
            if (i < data.size - 1) {
                result.append("${data[i].name}, ")
            } else {
                result.append(data[i].name)
            }
        }
        return result.toString()
    }
}