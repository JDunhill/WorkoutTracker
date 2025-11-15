package org.jdcoding.workouttracker.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object WorkoutGraph: Route

    @Serializable
    data object WorkoutList: Route

    @Serializable
    data class WorkoutDetail(val id: String): Route
}