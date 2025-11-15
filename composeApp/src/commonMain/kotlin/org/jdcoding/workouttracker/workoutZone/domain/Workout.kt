package org.jdcoding.workouttracker.workoutZone.domain

data class Workout(
    val id: String,
    val name: String,
    val sets: Int = 0,
    val reps: Int = 0,
    val duration: String? = "",
    val weight: Float?,
    val weightUnit: String?,
    val icon: String?
)
