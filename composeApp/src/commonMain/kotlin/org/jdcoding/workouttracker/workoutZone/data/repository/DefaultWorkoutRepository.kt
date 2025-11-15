package org.jdcoding.workouttracker.workoutZone.data.repository

import kotlinx.coroutines.flow.Flow
import org.jdcoding.core.domain.DataError
import org.jdcoding.core.domain.EmptyResult
import org.jdcoding.core.domain.Result
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.domain.WorkoutRepository

class DefaultWorkoutRepository: WorkoutRepository {

    override fun getFavouriteWorkouts(): Flow<List<Workout>> {
         TODO()
    }

    override suspend fun markAsFavourite(workout: Workout): EmptyResult<DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavourites(id: String) {
        TODO("Not yet implemented")
    }

    override fun isWorkoutFavourite(id: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun searchWorkouts(id: String): Result<List<Workout>, DataError.Local> {
        val emptyList = emptyList<Workout>()
        return Result.Success(emptyList)
    }
}