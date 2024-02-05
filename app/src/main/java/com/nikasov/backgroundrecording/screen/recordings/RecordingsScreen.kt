package com.nikasov.backgroundrecording.screen.recordings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikasov.backgroundrecording.components.RecordItem
import com.nikasov.backgroundrecording.components.SwipeToDelete
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun RecordingsScreen(
    navigator: DestinationsNavigator,
    viewModel: RecordingsViewModel = hiltViewModel(),
) {
    val records by viewModel.records.collectAsState()
    LaunchedEffect(true) {
        Log.i("HomeScreen", "HomeScreen: synced")
        viewModel.sync()
    }
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = records,
            key = { it.id }
        ) { item ->
            SwipeToDelete(
                onDelete = { viewModel.remove(item) },
                background = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.scrim)
                    )
                }
            ) {
                RecordItem(
                    item = item,
                    onClick = { viewModel.play(it) }
                )
            }
        }
    }
}