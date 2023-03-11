package com.dice.gameplayz.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.dice.core.domain.usecase.GameUseCase
import com.dice.gameplayz.favorite.ui.FavoriteViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = when {
        modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
            FavoriteViewModel(gameUseCase) as T
        }
        else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
    }
}