package org.jdcoding.workouttracker.workoutZone.presentation.workout_list.components


import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jdcoding.core.presentation.LightBlue
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.WorkoutListScreen
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.WorkoutListState
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.workouts

@Preview
@Composable
private fun WorkoutSearchBarPreview() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
    )
    {
        WorkoutSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun WorkoutListScreenPreview() {
    WorkoutListScreen(
        state = WorkoutListState(
            searchResults = workouts
        ), onAction = {}
    )
}