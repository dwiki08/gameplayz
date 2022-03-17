package com.dice.core.data.source.remote.service

import com.dice.core.data.source.remote.response.GameDetailResponse
import com.dice.core.data.source.remote.response.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("ordering") ordering: String? = null,
        @Query("dates") dates: String? = null,
        @Query("page") page: Int? = 1,
        @Query("page_size") pageSize: Int? = 20,
    ): Response<GamesResponse>

    @GET("games/{gameId}")
    suspend fun getGameDetail(@Path("gameId") gameId: String): Response<GameDetailResponse>

    @GET("games")
    suspend fun searchGames(@Query("search") search: String): Response<GamesResponse>
}