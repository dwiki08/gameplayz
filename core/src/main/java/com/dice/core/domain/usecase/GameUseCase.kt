package com.dice.core.domain.usecase

import com.dice.core.vo.Result
import com.dice.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getBestGames(): Flow<Result<List<Game>>>
    fun getDetailGame(id: Int): Flow<Result<Game>>
    fun searchGames(query: String): Flow<Result<List<Game>>>
    fun getFavoriteGames(): Flow<Result<List<Game>>>
    fun getGameDB(id: Int): Flow<Result<Game>>
    suspend fun addFavoriteGame(game: Game)
    suspend fun deleteFavoriteGame(game: Game)
}