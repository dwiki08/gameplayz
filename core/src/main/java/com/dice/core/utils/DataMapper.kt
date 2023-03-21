package com.dice.core.utils

import com.dice.core.data.source.local.entity.GameEntity
import com.dice.core.data.source.remote.response.GameDetailResponse
import com.dice.core.data.source.remote.response.GenresItemResponse
import com.dice.core.data.source.remote.response.ParentPlatformsItemResponse
import com.dice.core.domain.model.Game

@Suppress("unused")
object DataMapper {
    fun mapDtoToDomain(data: GameDetailResponse): Game {
        return Game(
            data.id,
            data.name,
            data.descriptionRaw ?: "",
            data.released,
            data.backgroundImage ?: "",
            platformDtoToDomain(data.parentPlatforms),
            genresDtoToString(data.genres),
            data.ratingsCount,
            data.rating ?: 0f,
            data.ratingTop ?: 0f,
            false
        )
    }

    fun mapEntityToDomain(data: GameEntity): Game {
        return Game(
            data.id,
            data.name,
            data.description,
            data.releaseDate,
            data.posterImage,
            platformEntityToDomain(data.platforms),
            data.genres,
            data.ratingsCount,
            data.rating,
            data.ratingTop,
            data.isFavorite
        )
    }

    fun mapDomainToEntity(data: Game): GameEntity {
        return GameEntity(
            data.id,
            data.name,
            data.description,
            data.releaseDate ?: "",
            data.posterImage,
            platformDomainToEntity(data.platforms),
            data.genres,
            data.ratingsCount,
            data.rating,
            data.ratingTop,
            data.isFavorite
        )
    }

    fun mapDtoToDomain(data: List<GameDetailResponse>): List<Game> = data.map { mapDtoToDomain(it) }

    fun mapEntityToDomain(data: List<GameEntity>): List<Game> = data.map { mapEntityToDomain(it) }

    fun mapDomainToEntity(data: List<Game>): List<GameEntity> = data.map { mapDomainToEntity(it) }

    private fun platformDtoToDomain(data: List<ParentPlatformsItemResponse>?): List<Game.Platform> {
        val platform = mutableListOf<Game.Platform>()
        data?.forEach { p ->
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

    private fun genresDtoToString(data: List<GenresItemResponse>?): String {
        val result = StringBuilder().append("")
        if (data != null) {
            for (i in data.indices) {
                if (i < data.size - 1) {
                    result.append("${data[i].name}, ")
                } else {
                    result.append(data[i].name)
                }
            }
        }
        return result.toString()
    }
}