package com.nikasov.backgroundrecording.components.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.nikasov.backgroundrecording.screen.destinations.RecordScreenDestination
import com.nikasov.backgroundrecording.screen.destinations.RecordingsScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String,
) {
    Record(RecordScreenDestination, Icons.Default.Home, "Record"),
    Recordings(RecordingsScreenDestination, Icons.AutoMirrored.Filled.List, "Recordings"),
}