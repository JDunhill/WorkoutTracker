package org.jdcoding.workouttracker.workoutZone.presentation.workout_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageInfo
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.ktor.utils.io.locks.synchronized
import org.jdcoding.core.presentation.Blue
import org.jdcoding.core.presentation.DarkGrey
import org.jdcoding.core.presentation.DesertWhite
import org.jdcoding.core.presentation.LightestGrey
import org.jdcoding.workouttracker.workoutZone.domain.Workout
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.components.WorkoutList
import org.jdcoding.workouttracker.workoutZone.presentation.workout_list.components.WorkoutSearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.module
import workouttracker.composeapp.generated.resources.Res
import workouttracker.composeapp.generated.resources.favourites
import workouttracker.composeapp.generated.resources.no_favourites
import workouttracker.composeapp.generated.resources.no_search_results
import workouttracker.composeapp.generated.resources.search_hint
import workouttracker.composeapp.generated.resources.search_results


@Composable
fun WorkoutListScreenRoot(
    viewModel: WorkoutListViewModel = koinViewModel(),
    onWorkoutClick: (Workout) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    WorkoutListScreen(
        state = state,
        onAction = { action  ->
            when(action) {
                is WorkoutListAction.OnWorkoutClick -> onWorkoutClick(action.workout)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
public fun WorkoutListScreen(
    state: WorkoutListState,
    onAction: (WorkoutListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }
    val searchResultsListState = rememberLazyListState()
    val favouritesListState = rememberLazyListState()


    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(WorkoutListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        WorkoutSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(WorkoutListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = Blue,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(WorkoutListAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.search_results),
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(WorkoutListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = DarkGrey,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.favourites),
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    pageIndex ->
                    Box(
                         modifier = Modifier
                             .fillMaxSize(),
                         contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage!!.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
//                                            Text(
//                                                text = stringResource(Res.string.no_search_results),
//                                                textAlign = TextAlign.Center,
//                                                style = MaterialTheme.typography.headlineSmall
//                                            )
                                            WorkoutList(
                                                workouts = workouts,
                                                onWorkoutClick = {
                                                    onAction(WorkoutListAction.OnWorkoutClick(it))
                                                },
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                        else -> {
                                            WorkoutList(
                                                workouts = state.searchResults,
                                                onWorkoutClick = {
                                                    onAction(WorkoutListAction.OnWorkoutClick(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultsListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if(state.favouriteWorkouts.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favourites),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                } else {
                                    WorkoutList(
                                        workouts = state.favouriteWorkouts,
                                        onWorkoutClick = {
                                            onAction(WorkoutListAction.OnWorkoutClick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favouritesListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
