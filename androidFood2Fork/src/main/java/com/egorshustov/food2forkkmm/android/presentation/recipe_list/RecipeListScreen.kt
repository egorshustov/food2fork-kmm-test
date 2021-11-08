package com.egorshustov.food2forkkmm.android.presentation.recipe_list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.components.RecipeList
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.components.SearchAppBar
import com.egorshustov.food2forkkmm.android.presentation.theme.AppTheme
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListEvent
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListState

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    onClickRecipeListItem: (Int) -> Unit
) {
    AppTheme(displayProgressBar = state.isLoading) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = state.query,
                    onQueryChanged = { onTriggerEvent(RecipeListEvent.OnUpdateQuery(it)) },
                    onExecuteSearch = { onTriggerEvent(RecipeListEvent.NewSearch) }
                )
            }
        ) {
            RecipeList(
                isLoading = state.isLoading,
                recipes = state.recipes,
                page = state.page,
                onTriggerNextPage = { onTriggerEvent(RecipeListEvent.NextPage) },
                onClickRecipeListItem = onClickRecipeListItem
            )
        }
    }
}