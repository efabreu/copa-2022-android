package com.efabreu.copa.catar

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.efabreu.copa.catar.domain.model.Match
import com.efabreu.copa.catar.domain.model.Stadium
import com.efabreu.copa.catar.domain.model.Team
import com.efabreu.copa.catar.ui.theme.Copa2022Theme
import java.time.LocalDateTime

@Composable
fun MainScreen(matches :List<Match>) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        MatchesList(matches = matches)
    }

}



@Composable
fun MatchesList(matches :List<Match>){
    LazyColumn(){
        items(matches){ match ->
            MatchCard(match)
        }
    }
}

@Composable
fun MatchCard(match: Match) {
    Card(){
        Text(text = match.name)
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

@Composable
fun Greeting(name: String) {
    Column {
        Text(text = "Hello $name!")
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    Copa2022Theme {
        MatchCard(match = Match(name = "1ª RODADA",
            date = LocalDateTime.now() ,
            id = 1.toString(),
            notificationEnabled = false,
            team1 = Team(flag = "galo", displayName = "Galo"),
            team2 = Team(flag = "porco", displayName = "Palmeiras"),
            stadium = Stadium(name = "Arena MRV", image = "teste")
        )
        )
    }
}