package org.jdcoding.workouttracker.workoutZone.data.mappers

import org.jdcoding.workouttracker.workoutZone.data.database.WorkoutEntity
import org.jdcoding.workouttracker.workoutZone.domain.Workout

fun Workout.toWorkoutEntity(): WorkoutEntity {
    return WorkoutEntity(
        id = id,
        name = name,
        icon = icon,
        weight = weight,
        weightUnit = weightUnit,
        duration = duration,
        sets = sets,
        reps = reps,
        isFavourite = isFavourite
    )
}

fun WorkoutEntity.toWorkout(): Workout {
    return Workout(
        id = id,
        name = name,
        icon = icon,
        weight = weight,
        weightUnit = weightUnit,
        duration = duration,
        sets = sets,
        reps = reps,
        isFavourite = isFavourite
    )
}