package org.jdcoding.workouttracker.workoutZone.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jdcoding.workouttracker.workoutZone.domain.Workout

class SelectedWorkoutViewModel: ViewModel() {

    private val _selectedWorkout = MutableStateFlow<Workout?>(null)
    val selectedWorkout = _selectedWorkout.asStateFlow()

    fun onSelectWorkout(workout: Workout?) {
        _selectedWorkout.value = workout
        println("SW vm =$workout")
        println("SW _vm" + _selectedWorkout.value?.id)
        println("SW _vm selected workout = " + selectedWorkout.value?.id)
    }
}