package org.jdcoding.workouttracker.workoutZone.presentation.workout_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jdcoding.core.presentation.DesertWhite
import org.jdcoding.core.presentation.LightGrey
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workouttracker.composeapp.generated.resources.Res
import workouttracker.composeapp.generated.resources.favourite_button
import workouttracker.composeapp.generated.resources.go_back
import workouttracker.composeapp.generated.resources.icons8_chest_50
import workouttracker.composeapp.generated.resources.icons8_failed_32
import workouttracker.composeapp.generated.resources.muscle_group_image
import workouttracker.composeapp.generated.resources.remove_from_favourites

@Composable
fun ImageBackground(
    icon: String?,
    isFavourite: Boolean,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
//    var image by remember {
//        mutableStateOf<Result<Painter>?>(null)
//    }
//
//    when (icon) {
//        "chest" -> image = Result.success(painterResource(Res.drawable.icons8_chest_50))
//        else -> image = Result.success(painterResource(Res.drawable.icons8_failed_32)) //TODO: add failure case
//    }
//
//    val painter = rememberAsyncImagePainter(
//        model = icon,
//        onSuccess = {
//            val size = it.painter.intrinsicSize
//            image = if(size.width > 1 && size.height > 1) {
//                Result.success(it.painter)
//            } else {
//                Result.failure(Exception("Invalid image dimensions"))
//            }
//        },
//        onError = {
//            it.result.throwable.printStackTrace()
//        }
//    )

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth()
                    .background(LightGrey)
            ) {
//                image?.getOrNull()?.let { painter ->
//                    Image(
//                        painter = painter,
//                        contentDescription = stringResource(Res.string.muscle_group_image),
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxSize()
//
//                    )
//                }
            }
            Box(
                modifier = Modifier
                    .weight(0.85f)
                    .fillMaxWidth()
                    .background(DesertWhite)
                // Spacer box
            ) {
                content()
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White
            )
        }
        IconButton(
            onClick = onFavouriteClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = if(isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if(isFavourite) stringResource(Res.string.remove_from_favourites) else stringResource(Res.string.favourite_button),
                tint = Color.White,
            )
        }
    }
}