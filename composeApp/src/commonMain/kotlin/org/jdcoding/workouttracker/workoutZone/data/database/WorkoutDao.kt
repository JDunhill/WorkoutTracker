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

    @Query("SELECT * FROM WorkoutEntity WHERE isFavourite = true")
    fun getFavouriteWorkouts(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM WorkoutEntity WHERE name LIKE :query")
    fun searchWorkouts(query: String): Flow<List<WorkoutEntity>>

    @Query("DELETE FROM WorkoutEntity WHERE id = :id")
    suspend fun deleteWorkout(id: String)

    @Query("UPDATE WorkoutEntity SET isFavourite = false WHERE id = :id AND isFavourite = true")
    suspend fun deleteFavouriteWorkout(id: String)

    @Query("UPDATE WorkoutEntity SET isFavourite = true WHERE id = :id AND isFavourite = false")
    suspend fun markAsFavourite(id: String)
}