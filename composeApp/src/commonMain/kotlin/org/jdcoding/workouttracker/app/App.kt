package org.jdcoding.workouttracker.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import org.jdcoding.workouttracker.workoutZone.presentation.SelectedWorkoutViewModel
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.WorkoutDetailAction
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.WorkoutDetailScreenRoot
import org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.WorkoutDetailViewModel
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.WorkoutListScreenRoot
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.WorkoutListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel



@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.WorkoutGraph
        ) {
            navigation<Route.WorkoutGraph>(
                startDestination = Route.WorkoutList
            ) {
                composable<Route.WorkoutList> {
                    val viewModel = koinViewModel<WorkoutListViewModel>()
                    val selectedWorkoutViewModel =
                        it.sharedKoinViewModel<SelectedWorkoutViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedWorkoutViewModel.onSelectWorkout(null)
                    }

                    WorkoutListScreenRoot(
                        viewModel = viewModel,
                        onWorkoutClick = { workout ->
                            selectedWorkoutViewModel.onSelectWorkout(workout)
                            navController.navigate(
                                Route.WorkoutDetail(workout.id)
                            )
                            println("Screen root sending = $workout")


                        }
                    )
                }

                composable<Route.WorkoutDetail> {

                    val selectedWorkoutViewModel =
                        it.sharedKoinViewModel<SelectedWorkoutViewModel>(navController)
                    val selectedWorkout by selectedWorkoutViewModel.selectedWorkout.collectAsStateWithLifecycle()
                    val workoutDetailViewModel = koinViewModel<WorkoutDetailViewModel>()

                    LaunchedEffect(selectedWorkout) {
                        selectedWorkout?.let {
                            workoutDetailViewModel.onAction(WorkoutDetailAction.OnSelectedWorkoutChange(it))
                        }

                    }

                    WorkoutDetailScreenRoot(
                        viewModel = workoutDetailViewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )


                }
            }

        }
    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}