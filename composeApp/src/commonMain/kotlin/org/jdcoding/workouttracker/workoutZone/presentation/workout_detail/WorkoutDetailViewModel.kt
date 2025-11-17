package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.workouts
import org.koin.core.module._scopedInstanceFactory
import kotlin.math.pow
import kotlin.math.roundToInt

class WorkoutDetailViewModel: ViewModel() {
    private val _state = MutableStateFlow(WorkoutDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: WorkoutDetailAction) {
        when(action) {
            is WorkoutDetailAction.OnSelectedWorkoutChange -> {
                _state.update { it.copy(
                  workout = action.workout
                ) }
            }
            is WorkoutDetailAction.OnFavouriteClick -> {

            }
            is WorkoutDetailAction.OnPlusClick -> {
                viewModelScope.launch {
                    // TODO: update using model
                    when(action.contentType) {
                        ContentType.REPS -> {
                            val currentReps = _state.value.workout?.reps
                            if (currentReps != null) {
                                updateReps(reps = currentReps + 1)
                            }
                        }
                        ContentType.SETS -> {
                            val currentSets = _state.value.workout?.sets
                            if (currentSets != null) {
                                updateSets(sets = currentSets + 1)
                            }
                        }
                        ContentType.WEIGHT -> {
                            val currentWeight = _state.value.workout?.weight
                            if (currentWeight != null && currentWeight > 0.0f) {
                                updateWeight(weight = currentWeight + 0.1f)
                            }
                        }
                    }

                }
            }
            is WorkoutDetailAction.OnMinusClick -> {
                viewModelScope.launch {
                    // TODO: update using model
                    when(action.contentType) {
                        ContentType.REPS -> {
                            val currentReps = _state.value.workout?.reps
                            if (currentReps != null && currentReps > 0) {
                                updateReps(reps = currentReps - 1)
                            }
                        }
                        ContentType.SETS -> {
                            val currentSets = _state.value.workout?.sets
                            if (currentSets != null && currentSets > 0) {
                                updateSets(sets = currentSets - 1)
                            }
                        }
                        ContentType.WEIGHT -> {
                            val currentWeight = _state.value.workout?.weight
                            if (currentWeight != null && currentWeight > 0.0) {
                                updateWeight(weight = currentWeight - 0.1)
                            }
                        }
                    }
                }
            }
            else -> Unit
        }
    }
    private fun updateReps(reps: Int) {
        _state.update {
            it.copy(
                workout = _state.value.workout?.copy(
                reps = reps
            )
        ) }
    }

    private fun updateSets(sets: Int) {
        _state.update {
            it.copy(
                workout = _state.value.workout?.copy(
                    sets = sets
                )
            ) }
    }

    private fun updateWeight(weight: Double) {
        _state.update {
            it.copy(
                workout = _state.value.workout?.copy(
                    weight = weight.roundTo(1)
                )
            ) }
    }

    private fun Double.roundTo(numFractionDigits: Int): Double {
        val factor = 10.0.pow(numFractionDigits.toDouble())
        return (this * factor).roundToInt() / factor
    }
}


