package com.dice.gameplayz.ui.search

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
class SearchViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {

    private val _gameList = MutableLiveData<Result<List<Game>>>()
    val gameList: LiveData<Result<List<Game>>>
        get() = _gameList

    private var query = ""

    fun searchGames(query: String) {
        when {
            query != this.query && query.isNotEmpty() -> {
                viewModelScope.launch {
                    gameUseCase.searchGames(query).collectLatest {
                        _gameList.postValue(it)
                    }
                }
            }
            query.isEmpty() -> _gameList.postValue(Result.Success(listOf()))
        }
        this.query = query
    }
}