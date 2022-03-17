package com.dice.gameplayz.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dice.core.domain.usecase.GameUseCase

class FavoriteViewModel constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {
    fun getFavoriteGames() = gameUseCase.getFavoriteGames().asLiveData()
}