package com.dice.core.domain.usecase

import com.dice.core.domain.model.Game
import com.dice.core.domain.repository.GameRepository
import com.dice.core.vo.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameUseCaseImpl @Inject constructor(private val repository: GameRepository) : GameUseCase {
    override fun getBestGames(page: Int): Flow<Result<List<Game>>> {
        return repository.getGamesRemote(
            page = page,
            pageSize = 20
        )
    }

    override fun getDetailGame(id: Int): Flow<Result<Game>> = repository.getGameDetailRemote(id)

    override fun searchGames(query: String): Flow<Result<List<Game>>> =
        repository.searchGames(query)

    override fun getFavoriteGames(): Flow<Result<List<Game>>> = repository.getFavoriteGames()

    override fun getGameDB(id: Int): Flow<Result<Game>> = repository.getGameDetailDB(id)

    override suspend fun addFavoriteGame(game: Game) =
        repository.addGame(game.copy(isFavorite = true))

    override suspend fun deleteFavoriteGame(game: Game) =
        repository.updateGame(game.copy(isFavorite = false))
}