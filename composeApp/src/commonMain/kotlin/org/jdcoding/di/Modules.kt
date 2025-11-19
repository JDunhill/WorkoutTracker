package org.jdcoding.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.jdcoding.workouttracker.workoutZone.data.database.DatabaseFactory
import org.jdcoding.workouttracker.workoutZone.data.database.WorkoutDatabase
import org.jdcoding.workouttracker.workoutZone.domain.WorkoutRepository
import org.jdcoding.workouttracker.workoutZone.data.repository.DefaultWorkoutRepository
import org.jdcoding.workouttracker.workoutZone.presentation.SelectedWorkoutViewModel
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.WorkoutDetailViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.WorkoutListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.bind

expect val platformModule: Module

val sharedModule = module {

    singleOf(::DefaultWorkoutRepository).bind<WorkoutRepository>()

    single { get<DatabaseFactory>().create()
        .setDriver(BundledSQLiteDriver())
        .build()
    }
    single { get<WorkoutDatabase>().workoutDao }

    viewModelOf(::WorkoutListViewModel)
    viewModelOf(::SelectedWorkoutViewModel)
    viewModelOf(::WorkoutDetailViewModel)
}