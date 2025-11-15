package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.WorkoutDetailAction
import org.jetbrains.compose.resources.painterResource
import workouttracker.composeapp.generated.resources.Res
import workouttracker.composeapp.generated.resources.heroicons_minus
import workouttracker.composeapp.generated.resources.minus_icon

@Composable
fun IncrementButtonsRow(
    modifier: Modifier,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        val image: Painter
        IconButton(
            modifier = Modifier,
            onClick = onPlusClick,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Increase button", //TODO: add to stringsxml
                tint = Color.LightGray
            )
        }
        IconButton(
            modifier = Modifier
                .size(40.dp),
            onClick = onMinusClick
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 8.dp),
                painter = painterResource(Res.drawable.minus_icon),
                contentDescription = "Decrease button", //TODO: add to stringsxml
                tint = Color.LightGray,
            )
        }
    }
}