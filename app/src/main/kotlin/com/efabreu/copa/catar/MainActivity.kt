package com.efabreu.copa.catar

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleObserver
import com.efabreu.copa.catar.domain.model.Match
import com.efabreu.copa.catar.extensions.observe
import com.efabreu.copa.catar.notification.scheduler.extensions.NotificationWorker
import com.efabreu.copa.catar.ui.theme.Copa2022Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), LifecycleObserver {

    lateinit var listaJogos :List<Match>
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()

        setContent {
            Copa2022Theme {
                val state by viewModel.state.collectAsState()
                MainScreen(matches = state.matches,
                    viewModel::toggleNotification,
                    setCalendarEvent = ::setCalendarEvent)
            }
        }
    }

    private fun observeViewModel(){
        viewModel.action.observe(this) {
            when(it){
                is UiAction.EmptyState -> TODO()
                is UiAction.NoMatchesFound -> TODO()
                is UiAction.TurnNotificationOff -> NotificationWorker.cancel(this, it.match)
                is UiAction.TurnNotificationOn -> NotificationWorker.start(this, it.match)
            }
        }
    }
    private fun setCalendarEvent(match: Match){
        val calendarIntent = Intent(Intent.ACTION_INSERT)
        calendarIntent.data = CalendarContract.Events.CONTENT_URI
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Copa do mundo - ${match.team1.displayName} X ${match.team2.displayName}")
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Jogo da copa do mundo, ${match.name}, entre ${match.team1.displayName} e ${match.team2.displayName}")
        calendarIntent.putExtra(CalendarContract.Events.ALL_DAY, true)
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, match.stadium.name)
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, match.date)
        this.startActivity(calendarIntent)
    }

}




