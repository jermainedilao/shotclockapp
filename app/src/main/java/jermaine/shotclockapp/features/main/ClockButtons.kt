package jermaine.shotclockapp.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jermaine.shotclockapp.R
import jermaine.shotclockapp.theme.Pink
import jermaine.shotclockapp.theme.VeryLightGrey

@Composable
fun ClockButtons(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 48.dp)
    ) {
        Box(
            modifier = Modifier
                .size(51.dp)
                .align(Alignment.CenterHorizontally)
                .background(color = Color.Transparent)
                .border(
                    width = 1.dp,
                    color = VeryLightGrey,
                    shape = CircleShape
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_replay_default_24dp),
                contentDescription = "Reset timer",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
        Row {
            TextButton(
                onClick = { },
                border = BorderStroke(1.dp, Pink),
                shape = CircleShape,
                modifier = Modifier.size(51.dp)
            ) {
                Text(
                    text = "-1",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClockButtonsPreview() {
    ClockButtons(Modifier.fillMaxSize())
}