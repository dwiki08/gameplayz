package com.dice.core.domain.repository

import com.dice.core.domain.model.Game
import com.dice.core.vo.Result
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGamesRemote(
        ordering: String? = null,
        dates: String? = null,
        page: Int? = 1,
        pageSize: Int? = 20
    ): Flow<Result<List<Game>>>

    fun getGameDetailRemote(id: Int): Flow<Result<Game>>

    fun searchGames(query: String): Flow<Result<List<Game>>>

    fun getGamesDB(): Flow<Result<List<Game>>>

    fun getGameDetailDB(id: Int): Flow<Result<Game>>

    fun getFavoriteGames(): Flow<Result<List<Game>>>

    suspend fun addGame(game: Game)

    suspend fun updateGame(game: Game)
}