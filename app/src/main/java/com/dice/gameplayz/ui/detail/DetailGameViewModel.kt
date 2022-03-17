package com.dice.gameplayz.ui.detail

import androidx.lifecycle.*
import com.dice.core.domain.model.Game
import com.dice.core.domain.usecase.GameUseCase
import com.dice.core.vo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailGameViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun getDetailGame(id: Int) = gameUseCase.getDetailGame(id).asLiveData()

    fun addFavorite(game: Game) {
        viewModelScope.launch {
            gameUseCase.addFavoriteGame(game)
            _isFavorite.postValue(true)
        }
    }

    fun deleteFavorite(game: Game) {
        viewModelScope.launch {
            gameUseCase.deleteFavoriteGame(game)
            _isFavorite.postValue(false)
        }
    }

    fun checkIsFavorite(id: Int) {
        viewModelScope.launch {
            gameUseCase.getGameDB(id).collectLatest {
                when (it) {
                    is Result.Success -> {
                        _isFavorite.postValue(it.data.isFavorite)
                    }
                    is Result.Error -> {
                        _isFavorite.postValue(false)
                    }
                    else -> {}
                }
            }
        }
    }
}