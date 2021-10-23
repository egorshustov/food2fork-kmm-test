package com.egorshustov.food2forkkmm.android.presentation.recipe_list

import androidx.compose.runtime.Composable
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.components.RecipeList
import com.egorshustov.food2forkkmm.android.presentation.theme.AppTheme
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListState

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onClickRecipeListItem: (Int) -> Unit
) {
    AppTheme(displayProgressBar = state.isLoading) {
        RecipeList(
            isLoading = state.isLoading,
            recipes = state.recipes,
            onClickRecipeListItem = onClickRecipeListItem
        )
    }
}