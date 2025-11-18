package org.jdcoding.workouttracker.workoutZone.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {

    @Upsert
    suspend fun upsert(workoutEntity: WorkoutEntity)

    @Query("SELECT * FROM WorkoutEntity")
    fun getWorkouts(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM WorkoutEntity WHERE id = :id")
    suspend fun getWorkout(id: String): WorkoutEntity?

    @Query("DELETE FROM WorkoutEntity WHERE id = :id")
    suspend fun deleteWorkout(id: String)
}