package org.jdcoding.workouttracker.workoutZone.domain

import kotlinx.coroutines.flow.Flow
import org.jdcoding.core.domain.DataError
import org.jdcoding.core.domain.EmptyResult
import org.jdcoding.core.domain.Result


interface WorkoutRepository {

    fun getFavouriteWorkouts(): Flow<List<Workout>>
    fun isWorkoutFavourite(id: String): Flow<Boolean>
    suspend fun markAsFavourite(workout: Workout): EmptyResult<DataError.Local>
    suspend fun deleteFromFavourites(id: String)
    suspend fun searchWorkouts(id: String): Result<List<Workout>, DataError.Local>
}