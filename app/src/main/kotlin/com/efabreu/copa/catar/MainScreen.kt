package com.efabreu.copa.catar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.efabreu.copa.catar.domain.model.Match
import com.efabreu.copa.catar.extensions.formatComp
import kotlin.reflect.KFunction1

@Composable
fun MainScreen(
    matches: List<Match>,
    toggleNotification: (Match) -> Unit,
    setCalendarEvent: KFunction1<Match, Unit>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MatchesList(matches = matches, toggleNotification, setCalendarEvent)
    }
}

@Composable
fun MatchesList(
    matches: List<Match>,
    toggleNotification: (Match) -> Unit,
    setCalendarEvent: (Match) -> Unit
){
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),){
        items(matches){ match ->
            MatchCard(match, toggleNotification, setCalendarEvent)
        }
    }
}

@Composable
fun MatchCard(match: Match, toggleNotification: (Match) -> Unit, setCalendarEvent: (Match) -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ){
        Box () {
            AsyncImage(
                model = match.stadium.image,
                contentDescription = match.stadium.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(150.dp)
            )
            Column (modifier = Modifier.align(Alignment.TopEnd)){
                Notification(match, toggleNotification)
                Calendar(match, setCalendarEvent)
            }
        }
        Box(modifier = Modifier.padding(8.dp)){
            MatchData(match)
        }
    }
}

@Composable
private fun MatchData(match: Match) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = match.name)
            Text(text = match.date.formatComp())
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = match.team1.displayName, fontSize = 24.sp)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = match.team1.flag, fontSize = 32.sp)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "X", fontSize = 20.sp)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = match.team2.flag, fontSize = 32.sp)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = match.team2.displayName, fontSize = 24.sp)
        }
    }
}

@Composable
private fun Notification(match: Match, toggleNotification: (Match) -> Unit) {
    Column(modifier = Modifier.padding(12.dp)) {
        val drwSource =
            if (match.notificationEnabled) R.drawable.notifications_active else R.drawable.notifications_none
        Image(
            painter = painterResource(id = drwSource),
            contentDescription = stringResource(id = R.string.description_notification),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .clickable { toggleNotification(match) }
                .size(24.dp)
                .clip(CircleShape),
        )
    }
}

@Composable
private fun Calendar(match: Match, setCalendarEvent: (Match) -> Unit) {
    Column(modifier = Modifier.padding(12.dp)) {
        Image(
            painter = painterResource(id = R.drawable.edit_calendar),
            contentDescription = stringResource(id = R.string.description_calendar),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .clickable { setCalendarEvent(match) }
                .size(24.dp)
                .clip(CircleShape),
        )
    }
}

@Composable
fun ProgressBar(isVisible :Boolean){
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp)
    )
}

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = Color(R.color.purple_700),
    backgroundColor: Color = Color(R.color.teal_200),
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    Box(
        modifier = modifier
            .clip(clipShape)
            .background(backgroundColor)
            .height(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//fun DefaultPreview() {
//    Copa2022Theme {
//        MatchCard(
//            match = Match(
//                id="BR-RS",
//                name="1ª RODADA",
//                stadium=Stadium(name="LUSAIL", image="https://digitalinnovationone.github.io/copa-2022-android/statics/lusali-stadium.png"),
//                team1=Team(flag="🇧🇷", displayName="BRA"),
//                team2=Team(flag="🇷🇸", displayName="SRB"),
//                date=LocalDateTime.now(),
//                notificationEnabled=false)
//        )
//
//
//    }
//}