package jermaine.shotclockapp.features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import jermaine.shotclockapp.R
import jermaine.shotclockapp.features.preferences.ShotClockPreferences
import jermaine.shotclockapp.theme.LightColors
import jermaine.shotclockapp.utils.*
import kotlinx.coroutines.launch

@Composable
fun Settings(navController: NavController) {
    MaterialTheme(colors = LightColors) {
        SettingsContent(navController)
    }
}

@Composable
private fun SettingsContent(navController: NavController) {
    var selectedTheme: ThemeType? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    val preferences = ShotClockPreferences(LocalContext.current)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.settings))
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            selectedTheme?.let {
                                scope.launch {
                                    preferences.saveTheme(it)
                                }
                            }
                            navController.navigateUp()
                        },
                        modifier = Modifier.testTag(TEST_TAG_BUTTON_BACK)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TEST_TAG_BUTTON_THEME),
                    elevation = ButtonDefaults
                        .elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            hoveredElevation = 0.dp,
                            focusedElevation = 0.dp
                        ),
                    contentPadding = PaddingValues(24.dp),
                    onClick = {
                        expanded = true
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.theme),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(.9f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(.1f)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(50.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    ThemeOptionsPopup(
                        expanded = expanded,
                        onItemSelected = { theme ->
                            selectedTheme = theme
                            expanded = false
                        },
                        onDismiss = {
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeOptionsPopup(
    expanded: Boolean,
    onItemSelected: (theme: ThemeType) -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        offset = DpOffset(24.dp, 0.dp),
        onDismissRequest = { onDismiss() },
        modifier = Modifier.testTag(TEST_TAG_POPUP_THEME)
    ) {
        DropdownMenuItem(
            onClick = {
                onItemSelected(ThemeType.Light)
            },
            modifier = Modifier.testTag(TEST_TAG_THEME_LIGHT)
        ) {
            Text(text = stringResource(id = R.string.light))
        }
        DropdownMenuItem(
            onClick = {
                onItemSelected(ThemeType.Dark)
            },
            modifier = Modifier.testTag(TEST_TAG_THEME_DARK)
        ) {
            Text(text = stringResource(id = R.string.dark))
        }
    }
}

@Preview
@Composable
private fun SettingsPreview() {
    MaterialTheme(colors = LightColors) {
        Settings(navController = rememberNavController())
    }
}