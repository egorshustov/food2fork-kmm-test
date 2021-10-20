package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.egorshustov.food2forkkmm.android.presentation.components.RecipeImage
import com.egorshustov.food2forkkmm.android.presentation.theme.AppTheme
import com.egorshustov.food2forkkmm.domain.model.Recipe

@Composable
fun RecipeDetailScreen(recipe: Recipe?) {
    AppTheme(displayProgressBar = false) {
        if (recipe == null) {
            Text("Unable to get the details of this recipe...")
        } else {
            RecipeImage(url = recipe.featuredImage, contentDescription = recipe.title)
        }
    }
}