package com.nikasov.backgroundrecording.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nikasov.common.extensions.millisToReadableTimeFormat
import com.nikasov.domain.repository.entity.Media

@Composable
fun RecordItem(
    item: Media,
    onClick: (Media) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp),)
            .clickable { onClick(item) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = item.duration.millisToReadableTimeFormat(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}