package jermaine.shotclockapp.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jermaine.shotclockapp.R
import jermaine.shotclockapp.theme.Pink
import jermaine.shotclockapp.theme.ShotClockTheme
import jermaine.shotclockapp.theme.VeryLightGrey

typealias OnPlayListener = () -> Unit
typealias OnDecreaseTimeListener = () -> Unit
typealias OnIncreaseTimeListener = () -> Unit
typealias OnResetTimeListener = () -> Unit

@Composable
fun ClockButtons(
    modifier: Modifier = Modifier,
    play: Boolean,
    onPlay: OnPlayListener,
    onIncreaseTime: OnIncreaseTimeListener,
    onDecreaseTime: OnDecreaseTimeListener,
    onResetTime: OnResetTimeListener
) {
    Column(
        modifier = modifier
            .padding(horizontal = 48.dp)
    ) {
        Button(
            border = BorderStroke(1.dp, VeryLightGrey),
            shape = CircleShape,
            modifier = Modifier
                .size(51.dp)
                .align(Alignment.CenterHorizontally),
            elevation = ButtonDefaults
                .elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp
                ),
            onClick = { onResetTime() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_replay_default_24dp),
                contentDescription = "Reset timer",
                tint = VeryLightGrey,
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                border = BorderStroke(1.dp, Pink),
                shape = CircleShape,
                modifier = Modifier.size(51.dp),
                elevation = ButtonDefaults
                    .elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp
                    ),
                onClick = { onDecreaseTime() }
            ) {
                Text(
                    text = "-1",
                    color = MaterialTheme.colors.onPrimary
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .size(80.dp),
                onClick = { onPlay() },
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    painter = if (play) {
                        painterResource(id = R.drawable.ic_pause_white_24dp)
                    } else {
                        painterResource(id = R.drawable.ic_play_arrow_white_44dp)
                    },
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
            Button(
                border = BorderStroke(1.dp, Pink),
                shape = CircleShape,
                modifier = Modifier.size(51.dp),
                elevation = ButtonDefaults
                    .elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp
                    ),
                onClick = { onIncreaseTime() }
            ) {
                Text(
                    text = "+1",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClockButtonsPreview() {
    ShotClockTheme {
        ClockButtons(
            modifier = Modifier.fillMaxSize(),
            play = false,
            onPlay = {},
            onIncreaseTime = {},
            onDecreaseTime = {},
            onResetTime = {},
        )
    }
}