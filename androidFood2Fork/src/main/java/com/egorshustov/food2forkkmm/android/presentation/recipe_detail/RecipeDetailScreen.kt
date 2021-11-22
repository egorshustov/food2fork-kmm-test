package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egorshustov.food2forkkmm.android.presentation.recipe_detail.components.RecipeView
import com.egorshustov.food2forkkmm.android.presentation.theme.AppTheme
import com.egorshustov.food2forkkmm.presentation.recipe_detail.RecipeDetailEvent
import com.egorshustov.food2forkkmm.presentation.recipe_detail.RecipeDetailState

@Composable
fun RecipeDetailScreen(
    state: RecipeDetailState,
    onTriggerEvent: (RecipeDetailEvent) -> Unit
) {
    AppTheme(displayProgressBar = state.isLoading) {
        when {
            state.recipe == null && state.isLoading -> {
                /*Loading*/
            }

            state.recipe == null && !state.isLoading -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "We were unable to retrieve the details for this recipe.\nTry resetting the app.",
                    style = MaterialTheme.typography.body1
                )
            }

            else -> {
                state.recipe?.let { RecipeView(recipe = it) }
            }
        }
    }
}