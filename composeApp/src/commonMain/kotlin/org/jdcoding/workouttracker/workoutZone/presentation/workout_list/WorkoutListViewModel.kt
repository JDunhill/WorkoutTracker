package org.jdcoding.workouttracker.workoutZone.presentation.workout_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jdcoding.core.domain.onError
import org.jdcoding.core.domain.onSuccess
import org.jdcoding.core.presentation.UiText
import org.jdcoding.core.presentation.toUiText
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.domain.WorkoutRepository

class WorkoutListViewModel(
    private val workoutRepository: WorkoutRepository
): ViewModel() {
    private val _state = MutableStateFlow(WorkoutListState())
    val state = _state
        .onStart {
            // TODO: currently dummy data
            workouts.forEach { workout ->
                workoutRepository.addWorkout(workout)
            }
            observeSearchQuery()
            observeFavouriteWorkouts()
            observeWorkouts()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var cachedWorkouts = mutableListOf<Workout>()
    private var searchJob: Job? = null
    private var observeFavouriteJob: Job? = null
    private var observeWorkoutsJob: Job? = null

    fun onAction(action: WorkoutListAction) {
        when(action) {
            is WorkoutListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is WorkoutListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
            is WorkoutListAction.OnWorkoutClick -> {

            }
        }
    }

    private fun observeFavouriteWorkouts() {
        observeFavouriteJob?.cancel()
        observeFavouriteJob = workoutRepository
            .getFavouriteWorkouts()
            .onEach { favouriteWorkouts ->
                _state.update { it.copy(
                    favouriteWorkouts = favouriteWorkouts
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeWorkouts() {
        observeWorkoutsJob?.cancel()
        observeWorkoutsJob = workoutRepository
            .getWorkouts()
            .onEach { workouts ->
                _state.update { it.copy(
                    currentWorkouts = workouts
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isNotEmpty() -> {
                        searchJob?.cancel()
                        searchJob = searchWorkouts(query)
                    }

                    else -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                searchResults = emptyList()
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchWorkouts(query: String)  = viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }
        workoutRepository
            .getWorkouts()
            .map {
                it.filter { it.name == query }
            }
            .map { searchResults ->
                println(searchResults)
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }

            }.launchIn(viewModelScope)


    }
}