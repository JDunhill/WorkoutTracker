package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdcoding.core.presentation.LightestGrey
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.ImageBackground
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.IncrementButtonsRow
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.ItemSize
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.WorkoutItem
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components.WorkoutTitle
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
        Box(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.75f)
                    .shadow(elevation = 5.dp, RoundedCornerShape(6.dp))
                    .background(color = LightestGrey)
                    .clip(shape = RoundedCornerShape(16.dp))

                    ,
                contentAlignment = Alignment.Center,
            ) {
                if (state.workout != null) {
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
                        verticalArrangement = Arrangement.spacedBy(60.dp)
                    ) {
                        Text(
                            text = state.workout.name,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 60.dp)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
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
                                    onAction(WorkoutDetailAction.OnPlusClick(contentType = ContentType.WEIGHT))
                                },
                                onMinusClick = {
                                    onAction(WorkoutDetailAction.OnMinusClick(contentType = ContentType.WEIGHT))
                                }
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
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
                                    onAction(WorkoutDetailAction.OnPlusClick(ContentType.SETS))
                                },
                                onMinusClick = {
                                    onAction(WorkoutDetailAction.OnMinusClick(ContentType.SETS))
                                }
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
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
                                    onAction(WorkoutDetailAction.OnPlusClick(ContentType.REPS))
                                },
                                onMinusClick = {
                                    onAction(WorkoutDetailAction.OnMinusClick(ContentType.REPS))
                                }
                            )
                        }

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
    }
}