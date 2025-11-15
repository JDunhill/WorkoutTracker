package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jdcoding.core.presentation.LightGrey

enum class ItemSize {
    SMALL, REGULAR, LARGE
}

@Composable
fun WorkoutItem(
    modifier: Modifier = Modifier,
    size: ItemSize = ItemSize.REGULAR,
    itemContent: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .widthIn(
                min = when(size) {
                    ItemSize.SMALL -> 60.dp
                    ItemSize.REGULAR -> 120.dp
                    ItemSize.LARGE -> 180.dp
                }
            )
            .clip(RoundedCornerShape(16.dp))
            .background(LightGrey)
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            itemContent()
        }
    }
}