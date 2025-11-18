package org.jdcoding.workouttracker.workoutZone.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val icon: String?,
    val weight: Double?,
    val weightUnit: String?,
    val duration: String?,
    val sets: Int?,
    val reps: Int?
) {


}