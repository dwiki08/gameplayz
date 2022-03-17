package com.dice.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dice.core.data.source.local.dao.GameDao
import com.dice.core.data.source.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}