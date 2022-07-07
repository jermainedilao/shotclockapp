package jermaine.shotclockapp.features.utils

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val TextColorKey = SemanticsPropertyKey<Long>("TextColor")
val SemanticsPropertyReceiver.textColor by TextColorKey