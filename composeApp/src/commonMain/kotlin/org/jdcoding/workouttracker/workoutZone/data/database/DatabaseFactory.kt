package org.jdcoding.workouttracker.workoutZone.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<WorkoutDatabase>

}