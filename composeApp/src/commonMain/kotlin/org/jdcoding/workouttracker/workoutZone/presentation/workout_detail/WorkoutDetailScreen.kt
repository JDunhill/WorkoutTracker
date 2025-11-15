package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.ImageBackground
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.IncrementButtonsRow
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.ItemSize
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.WorkoutItem
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.WorkoutTitle
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.WorkoutListAction
import org.jetbrains.compose.resources.stringResource
import workouttracker.composeapp.generated.resources.Res
import workouttracker.composeapp.generated.resources.reps
import workouttracker.composeapp.generated.resources.sets
import workouttracker.composeapp.generated.resources.weight

@Composable
fun WorkoutDetailScreenRoot(
    viewModel: WorkoutDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WorkoutDetailScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is WorkoutDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun WorkoutDetailScreen(
    state: WorkoutDetailState,
    onAction: (WorkoutDetailAction) -> Unit
) {
    ImageBackground(
        icon = state.workout?.icon,
        isFavourite = state.isFavourite,
        onBackClick = {
            onAction(WorkoutDetailAction.OnBackClick)
        },
        onFavouriteClick = {
            onAction(WorkoutDetailAction.OnFavouriteClick)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        if(state.workout != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 30.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = state.workout.name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 70.dp)
                )
                WorkoutItem(
                    size = ItemSize.LARGE,
                ) {
                    WorkoutTitle(
                        title = stringResource(Res.string.weight),
                    ) {
                        Text(
                            text = state.workout.weight.toString()
                        )
                    }
                }
                IncrementButtonsRow(
                    modifier = Modifier,
                    onPlusClick = {
                        onAction(WorkoutDetailAction.OnPlusClick(true))
                    },
                    onMinusClick = {
                        onAction(WorkoutDetailAction.OnMinusClick(false))
                    }
                )

                WorkoutItem {
                    WorkoutTitle(
                        title = stringResource(Res.string.sets)
                    ) {
                        Text(
                            text = state.workout.sets.toString()
                        )
                    }
                }

                IncrementButtonsRow(
                    modifier = Modifier,
                    onPlusClick = {
                        onAction(WorkoutDetailAction.OnPlusClick(false))
                    },
                    onMinusClick = {
                        onAction(WorkoutDetailAction.OnMinusClick(false))
                    }
                )

                WorkoutItem {
                    WorkoutTitle(
                        title = stringResource(Res.string.reps)
                    ) {
                        Text(
                            text = state.workout.reps.toString()
                        )
                    }
                }

                IncrementButtonsRow(
                    modifier = Modifier,
                    onPlusClick = {
                        onAction(WorkoutDetailAction.OnPlusClick(true))
                    },
                    onMinusClick = {
                        onAction(WorkoutDetailAction.OnMinusClick(false))
                    }
                )

            }
        } else {
            Text(
                text = "Workout null!",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}