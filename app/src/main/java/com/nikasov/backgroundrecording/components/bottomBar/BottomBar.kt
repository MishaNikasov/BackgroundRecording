package com.nikasov.backgroundrecording.components.bottomBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.nikasov.backgroundrecording.screen.NavGraphs
import com.nikasov.backgroundrecording.screen.appCurrentDestinationAsState
import com.nikasov.backgroundrecording.screen.destinations.Destination
import com.nikasov.backgroundrecording.screen.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value ?: NavGraphs.root.startAppDestination
    fun navigate(route: DirectionDestinationSpec) {
        if (currentDestination == route) return
        navController.graph.startDestinationRoute?.let { navController.popBackStack(it, false) }
        navController.navigate(route) { launchSingleTop = true }
    }
    NavigationBar {
        BottomBarDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = { navigate(destination.direction) },
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) },
            )
        }
    }
}