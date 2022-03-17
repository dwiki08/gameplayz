package com.dice.core.di

import com.dice.core.data.repository.GameRepositoryImpl
import com.dice.core.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gameRepository: GameRepositoryImpl): GameRepository

}