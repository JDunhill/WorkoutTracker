package org.jdcoding.core.domain

sealed interface DataError: Error {
    enum class Remote: DataError {
        NO_INTERNET,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN,
        NO_WORKOUT_FOUND
    }
}