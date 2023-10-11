package com.efabreu.copa.catar

import androidx.lifecycle.viewModelScope
import com.efabreu.copa.catar.core.BaseViewModel
import com.efabreu.copa.catar.domain.model.Match
import com.efabreu.copa.catar.domain.usecase.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) :  BaseViewModel<UiState, UiAction>(UiState()) {

    init {
        loadMaches()
    }

    private fun loadMaches (): Job {
        return viewModelScope.launch {
            getMatchesUseCase.execute()
                .flowOn(Dispatchers.IO)
                .catch {
                }.collect{
                    setState {
                        copy(matches = it)
                    }
                }
        }
    }

}

data class UiState (
    val matches :List<Match> = listOf()
)

sealed class UiAction {

}
