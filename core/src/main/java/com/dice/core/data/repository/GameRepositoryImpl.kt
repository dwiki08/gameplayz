package com.dice.core.data.repository

import com.dice.core.data.source.local.LocalDataSource
import com.dice.core.data.source.remote.RemoteDataSource
import com.dice.core.domain.model.Game
import com.dice.core.domain.repository.GameRepository
import com.dice.core.utils.DataMapper.toEntity
import com.dice.core.utils.DataMapper.toModel
import com.dice.core.vo.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : GameRepository {
    override fun getGamesRemote(
        ordering: String?,
        dates: String?,
        page: Int?,
        pageSize: Int?
    ): Flow<Result<List<Game>>> {
        return flow {
            emit(Result.Loading)
            when (val remoteSource =
                remoteDataSource.getGames(ordering, dates, page, pageSize).first()) {
                is Result.Success -> {
                    emit(Result.Success(remoteSource.data.toModel()))
                }
                is Result.Error -> {
                    emit(Result.Error(remoteSource.code, remoteSource.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun getGameDetailRemote(id: Int): Flow<Result<Game>> {
        return flow {
            emit(Result.Loading)
            when (val remoteSource =
                remoteDataSource.getDetailGame(id).first()) {
                is Result.Success -> {
                    emit(Result.Success(remoteSource.data.toModel()))
                }
                is Result.Error -> {
                    emit(Result.Error(remoteSource.code, remoteSource.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun searchGames(query: String): Flow<Result<List<Game>>> {
        return flow {
            emit(Result.Loading)
            when (val remoteSource =
                remoteDataSource.searchGames(query).first()) {
                is Result.Success -> {
                    emit(Result.Success(remoteSource.data.toModel()))
                }
                is Result.Error -> {
                    emit(Result.Error(remoteSource.code, remoteSource.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun getGamesDB(): Flow<Result<List<Game>>> {
        return flow {
            emit(Result.Loading)
            val result = localDataSource.getGames()
            emit(Result.Success(result.toModel()))
        }
    }

    override fun getGameDetailDB(id: Int): Flow<Result<Game>> {
        return flow {
            emit(Result.Loading)
            val result = localDataSource.getGame(id)
            if (result != null) {
                emit(Result.Success(result.toModel()))
            } else {
                emit(Result.Error(null, "Data Not Found"))
            }
        }
    }

    override fun getFavoriteGames(): Flow<Result<List<Game>>> {
        return getGamesDB().map { result ->
            return@map if (result is Result.Success) {
                Result.Success(result.data.filter { it.isFavorite })
            } else {
                result
            }
        }
    }

    override suspend fun addGame(game: Game) {
        localDataSource.addGame(game.toEntity())
    }

    override suspend fun updateGame(game: Game) {
        localDataSource.updateGame(game.toEntity())
    }
}