package org.jdcoding.workouttracker

import androidx.compose.ui.window.ComposeUIViewController
import org.jdcoding.di.initKoin
import org.jdcoding.workouttracker.app.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }