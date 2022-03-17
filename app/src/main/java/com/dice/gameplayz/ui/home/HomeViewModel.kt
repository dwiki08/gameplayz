package com.dice.gameplayz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.core.domain.model.Game
import com.dice.core.domain.usecase.GameUseCase
import com.dice.core.vo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gamesUseCase: GameUseCase
) : ViewModel() {

    private val _gameList = MutableLiveData<Result<List<Game>>>()
    val gameList: LiveData<Result<List<Game>>>
        get() = _gameList

    fun getBestGames(refresh: Boolean = false) {
        if (gameList.value == null || refresh) {
            viewModelScope.launch {
                gamesUseCase.getBestGames().collectLatest {
                    _gameList.postValue(it)
                }
            }
        }
    }
}