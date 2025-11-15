package org.jdcoding.workouttracker.workoutZone.presentation.workout_list

import org.jdcoding.workouttracker.workoutZone.domain.Workout

sealed interface WorkoutListAction {
    data class OnSearchQueryChange(val query: String): WorkoutListAction
    data class OnWorkoutClick(val workout: Workout): WorkoutListAction
    data class OnTabSelected(val index: Int): WorkoutListAction
}