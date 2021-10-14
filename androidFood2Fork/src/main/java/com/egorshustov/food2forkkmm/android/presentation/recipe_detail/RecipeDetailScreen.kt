package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.egorshustov.food2forkkmm.domain.model.Recipe

@Composable
fun RecipeDetailScreen(recipe: Recipe?) {
    Text(text = recipe?.toString() ?: "Unable to get the details of this recipe...")
}