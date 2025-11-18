package org.jdcoding.workouttracker.workoutZone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutEntity::class],
    version = 1
)

abstract class WorkoutDatabase: RoomDatabase() {
    abstract val workoutDao: WorkoutDao

    companion object {
        const val DB_NAME = "workout.db"
    }
}