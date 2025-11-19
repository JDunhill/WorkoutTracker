package org.jdcoding.workouttracker.workoutZone.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jdcoding.core.domain.DataError
import org.jdcoding.core.domain.EmptyResult
import org.jdcoding.core.domain.Result
import org.jdcoding.workouttracker.workoutZone.data.database.WorkoutDao
import org.jdcoding.workouttracker.workoutZone.data.mappers.toWorkout
import org.jdcoding.workouttracker.workoutZone.data.mappers.toWorkoutEntity
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.domain.WorkoutRepository

class DefaultWorkoutRepository(
    private val workoutDao: WorkoutDao
): WorkoutRepository {

    override fun getFavouriteWorkouts(): Flow<List<Workout>> {
         return workoutDao
             .getFavouriteWorkouts()
             .map { workoutEntities ->
                 workoutEntities.map { it.toWorkout() }
             }
    }

    override fun getWorkouts(): Flow<List<Workout>> {
        return workoutDao
            .getWorkouts()
            .map { workoutEntities ->
                workoutEntities.map { it.toWorkout() }
            }
    }

    override suspend fun markAsFavourite(id: String) {
            workoutDao.markAsFavourite(id)
    }

    override suspend fun deleteFromFavourites(id: String) {
        workoutDao.deleteFavouriteWorkout(id)
    }

    override suspend fun deleteWorkout(id: String) {
        workoutDao.deleteWorkout(id)
    }

    override fun isWorkoutFavourite(id: String): Flow<Boolean> {
        return workoutDao
            .getFavouriteWorkouts()
            .map { workoutEntities ->
                workoutEntities.any { it.id == id }
            }
    }

    override suspend fun addWorkout(workout: Workout): EmptyResult<DataError.Local> {
        return try {
            workoutDao.upsert(workout.toWorkoutEntity())
            Result.Success(Unit)
        } catch(e: SQLiteException) {
            // CMP doesn't support specific SQLite exceptions (i.e. no storage space), so return generic
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun searchWorkouts(id: String): Result<List<Workout>, DataError.Local> {
        val emptyList = emptyList<Workout>()
        return Result.Success(emptyList)
    }
}