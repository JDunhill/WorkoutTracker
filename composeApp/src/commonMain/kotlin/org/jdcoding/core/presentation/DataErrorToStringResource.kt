package org.jdcoding.core.presentation

import org.jdcoding.core.domain.DataError
import workouttracker.composeapp.generated.resources.Res
import workouttracker.composeapp.generated.resources.error_disk_full
import workouttracker.composeapp.generated.resources.error_no_internet
import workouttracker.composeapp.generated.resources.error_unknown

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }
    return UiText.StringResourceId(stringRes)
}