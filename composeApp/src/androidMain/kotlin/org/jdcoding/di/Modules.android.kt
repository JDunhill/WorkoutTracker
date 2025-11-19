package org.jdcoding.di

import org.jdcoding.workouttracker.workoutZone.data.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.ext.koin.androidApplication


actual val platformModule: Module
    get() = module {
        single { DatabaseFactory(androidApplication()) }
    }