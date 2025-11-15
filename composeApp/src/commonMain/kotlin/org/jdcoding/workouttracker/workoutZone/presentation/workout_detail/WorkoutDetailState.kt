package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import org.jdcoding.workouttracker.workoutZone.domain.Workout

data class WorkoutDetailState(
    val isLoading: Boolean = true,
    val isFavourite: Boolean = false,
    val workout: Workout? = null
) {



}