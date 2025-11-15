package org.jdcoding.workouttracker.workoutZone.presentation.workout_list

import org.jdcoding.core.presentation.UiText
import org.jdcoding.workouttracker.workoutZone.domain.Workout

data class WorkoutListState(
    val searchQuery: String = "",
    val searchResults: List<Workout> = workouts, // TODO: using dummy data
    val favouriteWorkouts: List<Workout> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    var errorMessage: UiText? = null
)


public val workouts = (1 .. 20).map {
    Workout(
        id = it.toString(),
        name = "Workout $it",
        sets = (0..4).random(),
        reps = (0..12).random(),
        duration = (0..60).random().toString(),
        weight = 15.0f,
        weightUnit = "kg",
        icon = "chest"
    )
}
