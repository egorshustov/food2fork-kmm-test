package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun RecipeDetailScreen(recipeId: Int?) {
    if (recipeId == null) {
        Text(text = "ERROR")
    } else {
        Text(text = "RecipeDetailScreen: $recipeId")
    }
}