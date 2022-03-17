package com.dice.core.data.source.remote

import com.dice.core.data.source.remote.response.GameDetailResponse
import com.dice.core.data.source.remote.service.ApiService
import com.dice.core.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun getGames(
        ordering: String? = null,
        dates: String? = null,
        page: Int? = 1,
        pageSize: Int? = 20
    ): Flow<Result<List<GameDetailResponse>>> {
        return flow {
            try {
                val request = apiService.getGames(ordering, dates, page, pageSize)
                if (request.isSuccessful) {
                    request.body()?.let { emit(Result.Success(it.results)) }
                } else {
                    emit(Result.Error(request.code(), request.message()))
                }
            } catch (e: Exception) {
                emit(Result.Error(null, e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailGame(id: Int): Flow<Result<GameDetailResponse>> {
        return flow {
            try {
                val request = apiService.getGameDetail(id.toString())
                if (request.isSuccessful) {
                    request.body()?.let { emit(Result.Success(it)) }
                } else {
                    emit(Result.Error(request.code(), request.message()))
                }
            } catch (e: Exception) {
                emit(Result.Error(null, e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchGames(query: String): Flow<Result<List<GameDetailResponse>>> {
        return flow {
            try {
                val request = apiService.searchGames(query)
                if (request.isSuccessful) {
                    request.body()?.let { emit(Result.Success(it.results)) }
                } else {
                    emit(Result.Error(request.code(), request.message()))
                }
            } catch (e: Exception) {
                emit(Result.Error(null, e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }
}