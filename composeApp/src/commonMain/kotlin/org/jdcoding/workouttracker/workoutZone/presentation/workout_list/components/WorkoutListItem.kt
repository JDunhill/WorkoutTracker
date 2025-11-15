package org.jdcoding.workouttracker.workoutZone.presentation.workout_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jdcoding.core.presentation.LightGrey
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jetbrains.compose.resources.painterResource
import workouttracker.composeapp.generated.resources.Res
import workouttracker.composeapp.generated.resources.icons8_chest_50
import workouttracker.composeapp.generated.resources.icons8_failed_32

@Composable
fun WorkoutListItem (
    workout: Workout,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .clickable(onClick = onClick),
        color = LightGrey.copy(alpha = 0.2f)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Box(
                modifier = Modifier
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {

                val image: Painter
                when (workout.icon) {
                    "chest" -> image = painterResource(Res.drawable.icons8_chest_50)
                    else -> image = painterResource(Res.drawable.icons8_failed_32)
                }

                Image(
                    painter = image,
                    contentDescription = workout.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(
                            ratio = 0.65f,
                            matchHeightConstraintsFirst = true
                        )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                workout.sets?.let {
                    Text(
                        text = workout.sets.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Icon(

                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
            )
        }

    }
}
