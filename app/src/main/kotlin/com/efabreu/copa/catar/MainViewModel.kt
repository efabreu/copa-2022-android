package com.efabreu.copa.catar

import android.content.Intent
import android.provider.CalendarContract
import androidx.lifecycle.viewModelScope
import com.efabreu.copa.catar.core.BaseViewModel
import com.efabreu.copa.catar.domain.model.Match
import com.efabreu.copa.catar.domain.usecase.DisableNotificationUseCase
import com.efabreu.copa.catar.domain.usecase.EnableNotificationUseCase
import com.efabreu.copa.catar.domain.usecase.GetMatchesUseCase
import com.efabreu.copa.catar.remote.NotFoundException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase
) :  BaseViewModel<UiState, UiAction>(UiState()) {

    init {
        loadMaches()
    }

    private fun loadMaches (): Job {
        return viewModelScope.launch {
            getMatchesUseCase.execute()
                .flowOn(Dispatchers.IO)
                .catch {
                    when(it){
                        is NotFoundException -> sendAction(UiAction.NoMatchesFound(it.message ?: "Não foram encotradas nenhuma partida."))
                        is Exception -> sendAction(UiAction.EmptyState)
                    }
                }
                .collect{
                    setState {
                        copy(matches = it)
                        }
                    }
            }
    }
    fun toggleNotification(match: Match) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    val action = if (match.notificationEnabled) {
                        disableNotificationUseCase.execute(match.id)
                        UiAction.TurnNotificationOff(match)
                    } else {
                        enableNotificationUseCase.execute(match.id)
                        UiAction.TurnNotificationOn(match)
                    }
                    sendAction(action)
                }
            }
        }
    }

    fun onClickNewCalendarEvent(match :Match) :Intent{
        val calendarIntent = Intent(Intent.ACTION_INSERT)
        calendarIntent.data = CalendarContract.Events.CONTENT_URI
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Copa do mundo - ${match.team1.displayName} X ${match.team2.displayName}")
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Jogo da copa do mundo, ${match.name}, entre ${match.team1.displayName} e ${match.team2.displayName}")
        calendarIntent.putExtra(CalendarContract.Events.ALL_DAY, true)
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, match.stadium.name)
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, match.date)
        return calendarIntent
    }

}

data class UiState (
    val matches :List<Match> = listOf()
)

sealed interface UiAction {
    data class NoMatchesFound (val message :String) :UiAction
    data object EmptyState :UiAction
    data class TurnNotificationOn (val match :Match) :UiAction
    data class TurnNotificationOff (val match :Match) :UiAction
}
