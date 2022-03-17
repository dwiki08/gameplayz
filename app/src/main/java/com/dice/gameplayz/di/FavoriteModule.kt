package com.dice.gameplayz.di

import com.dice.core.domain.usecase.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModule {

    fun gameUseCase(): GameUseCase
}