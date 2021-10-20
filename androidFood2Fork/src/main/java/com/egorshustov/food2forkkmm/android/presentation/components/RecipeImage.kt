package com.egorshustov.food2forkkmm.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

const val RECIPE_IMAGE_HEIGHT = 260

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeImage(url: String, contentDescription: String) {
    val painter = rememberImagePainter(data = url)

    Box {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(RECIPE_IMAGE_HEIGHT.dp),
        )
    }

    when (painter.state) {
        ImagePainter.State.Empty -> {
        }

        is ImagePainter.State.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(RECIPE_IMAGE_HEIGHT.dp),
            ) {
                // empty for white background
            }
        }

        is ImagePainter.State.Success -> {
            // do something on success
        }

        is ImagePainter.State.Error -> {
            // show error or whatever
        }
    }
}