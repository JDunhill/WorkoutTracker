package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jdcoding.workouttracker.app.Route
import org.jdcoding.workouttracker.workoutZone.domain.WorkoutRepository
import kotlin.math.pow
import kotlin.math.roundToInt

class WorkoutDetailViewModel(
    private val workoutRepository: WorkoutRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val workoutId = savedStateHandle.toRoute<Route.WorkoutDetail>().id

    private val _state = MutableStateFlow(WorkoutDetailState())
    val state = _state
        .onStart {
            observeFavouriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    fun onAction(action: WorkoutDetailAction) {
        when(action) {
            is WorkoutDetailAction.OnSelectedWorkoutChange -> {
                _state.update { it.copy(
                  workout = action.workout
                ) }
            }
            is WorkoutDetailAction.OnFavouriteClick -> {
                viewModelScope.launch {
                    if(state.value.isFavourite) {
                        workoutRepository.deleteFromFavourites(workoutId)
                    } else {
                        state.value.workout?.let { workout ->
                            workoutRepository.markAsFavourite(workout.id)
                        }
                    }
                }
            }
            is WorkoutDetailAction.OnPlusClick -> {
                viewModelScope.launch {
                    // TODO: update using model
                    when(action.contentType) {
                        ContentType.REPS -> {
                            state.value.workout?.reps?.let { reps ->
                                updateReps(reps = reps + 1)

                            }
                        }
                        ContentType.SETS -> {
                            state.value.workout?.sets?.let { sets ->
                                updateSets(sets = sets + 1)
                            }
                        }
                        ContentType.WEIGHT -> {
                            state.value.workout?.weight?.let { weight ->
                                updateWeight(weight = weight + 1)
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
                            state.value.workout?.reps?.let { reps ->
                                if (reps > 0) {
                                    updateReps(reps = reps - 1)
                                }
                            }
                        }
                        ContentType.SETS -> {
                            state.value.workout?.sets?.let { sets ->
                                if (sets > 0) {
                                    updateSets(sets = sets - 1)
                                }
                            }
                        }
                        ContentType.WEIGHT -> {
                            state.value.workout?.weight?.let { weight ->
                                if (weight > 0) {
                                    updateWeight(weight = weight - 1)
                                }
                            }
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private fun observeFavouriteStatus() {
        workoutRepository
            .isWorkoutFavourite(workoutId)
            .onEach { isFavourite ->
                   _state.update { it.copy(
                       isFavourite = isFavourite
                   ) }
            }
            .launchIn(viewModelScope)
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


