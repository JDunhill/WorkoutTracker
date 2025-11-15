package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.workouts
import org.koin.core.module._scopedInstanceFactory

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
                    val current = _state.value.workout?.reps
                        if(current != null) {
                                updateReps(reps = current + 1)
                        }

                }
            }
            is WorkoutDetailAction.OnMinusClick -> {

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
}


