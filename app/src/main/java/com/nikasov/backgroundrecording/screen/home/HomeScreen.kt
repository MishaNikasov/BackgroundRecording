package com.nikasov.backgroundrecording.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikasov.backgroundrecording.components.RecordItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val records by viewModel.records.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(records) { item ->
            val state = rememberDismissState(
                confirmValueChange = {
                    if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                        viewModel.remove(item)
                        true
                    } else false
                }
            )
            SwipeToDismiss(state = state, background = { }, dismissContent = {
                RecordItem(
                    item = item,
                    onClick = { viewModel.play(it) }
                )
            })
        }
    }
}