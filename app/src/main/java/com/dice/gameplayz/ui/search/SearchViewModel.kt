package com.dice.gameplayz.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.core.vo.Result
import com.dice.core.domain.model.Game
import com.dice.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {

    private val _gameList = MutableLiveData<Result<List<Game>>>()
    val gameList: LiveData<Result<List<Game>>>
        get() = _gameList

    fun searchGames(query: String) {
        viewModelScope.launch {
            gameUseCase.searchGames(query).collectLatest {
                _gameList.postValue(it)
            }
        }
    }
}