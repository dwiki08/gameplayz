package com.dice.gameplayz.di

import com.dice.core.domain.usecase.GameUseCase
import com.dice.core.domain.usecase.GameUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideGameUseCase(gameRepository: GameUseCaseImpl): GameUseCase
}