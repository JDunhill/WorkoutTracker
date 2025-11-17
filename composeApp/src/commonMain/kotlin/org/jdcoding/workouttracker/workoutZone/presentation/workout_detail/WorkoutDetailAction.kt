package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail

import org.jdcoding.workouttracker.app.Route
import org.jdcoding.workouttracker.workoutZone.domain.Workout

interface WorkoutDetailAction {
    data object OnBackClick: WorkoutDetailAction
    data object OnFavouriteClick: WorkoutDetailAction
    data class OnPlusClick(val contentType: ContentType): WorkoutDetailAction
    data class OnMinusClick(val contentType: ContentType): WorkoutDetailAction
    data class OnSelectedWorkoutChange(val workout: Workout): WorkoutDetailAction

}

enum class ContentType {
    WEIGHT, SETS, REPS
}