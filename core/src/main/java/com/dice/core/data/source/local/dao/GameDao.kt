package com.dice.core.data.source.local.dao

import androidx.room.*
import com.dice.core.data.source.local.entity.GameEntity

@Dao
interface GameDao {
    @Query("SELECT * FROM tb_game")
    suspend fun getGames(): List<GameEntity>

    @Query("SELECT * FROM tb_game WHERE is_favorite = 1")
    suspend fun getFavoriteGames(): List<GameEntity>

    @Query("SELECT * FROM tb_game WHERE game_id = :id")
    suspend fun getGame(id: Int): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(vararg entity: GameEntity)

    @Update(entity = GameEntity::class)
    suspend fun updateGame(game: GameEntity)
}