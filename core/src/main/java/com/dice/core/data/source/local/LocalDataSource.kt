package com.dice.core.data.source.local

import com.dice.core.data.source.local.dao.GameDao
import com.dice.core.data.source.local.entity.GameEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val gameDao: GameDao
) {
    suspend fun addGame(gameEntity: GameEntity) {
        gameDao.insertGames(*listOf(gameEntity).toTypedArray())
    }

    suspend fun updateGame(gameEntity: GameEntity) {
        gameDao.updateGame(gameEntity)
    }

    suspend fun getGames(): List<GameEntity> {
        return gameDao.getGames()
    }

    suspend fun getFavoriteGames(): List<GameEntity> {
        return gameDao.getFavoriteGames()
    }

    suspend fun getGame(id: Int): GameEntity? {
        return gameDao.getGame(id)
    }
}