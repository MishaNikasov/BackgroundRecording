package com.nikasov.backgroundrecording.screen.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nikasov.backgroundrecording.components.bottomBar.BottomBar
import com.nikasov.backgroundrecording.screen.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }

}