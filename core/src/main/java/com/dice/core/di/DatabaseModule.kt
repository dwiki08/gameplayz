package com.dice.core.di

import android.content.Context
import androidx.room.Room
import com.dice.core.BuildConfig
import com.dice.core.data.source.local.dao.GameDao
import com.dice.core.data.source.local.db.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes(BuildConfig.DATABASE_PASSPHARSE.toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(context, GameDatabase::class.java, BuildConfig.DATABASE)
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()
}