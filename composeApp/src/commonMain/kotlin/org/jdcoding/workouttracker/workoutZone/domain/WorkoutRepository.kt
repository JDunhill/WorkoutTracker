package org.jdcoding.workouttracker.workoutZone.domain

import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.jdcoding.core.domain.DataError
import org.jdcoding.core.domain.EmptyResult
import org.jdcoding.core.domain.Result
import workouttracker.composeapp.generated.resources.Res


interface WorkoutRepository {

    fun getFavouriteWorkouts(): Flow<List<Workout>>
    fun getWorkouts(): Flow<List<Workout>>
    fun isWorkoutFavourite(id: String): Flow<Boolean>
    suspend fun addWorkout(workout: Workout): EmptyResult<DataError.Local>
    suspend fun markAsFavourite(id: String)
    suspend fun deleteFromFavourites(id: String)
    suspend fun deleteWorkout(id: String)
    fun searchWorkouts(query: String): Flow<List<Workout>>
}