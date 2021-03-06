package com.egorshustov.food2forkkmm.android.presentation.recipe_list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.components.RecipeList
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.components.SearchAppBar
import com.egorshustov.food2forkkmm.android.presentation.theme.AppTheme
import com.egorshustov.food2forkkmm.presentation.recipe_list.FoodCategoryUtil
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListEvent
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListState

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    onClickRecipeListItem: (Int) -> Unit
) {
    AppTheme(
        displayProgressBar = state.isLoading,
        messageQueue = state.queue,
        onRemoveHeadMessageFromQueue = { onTriggerEvent(RecipeListEvent.OnRemoveHeadMessageFromQueue) }
    ) {
        val foodCategories = remember { FoodCategoryUtil.getAllFoodCategories() }
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = state.query,
                    categories = foodCategories,
                    selectedCategory = state.selectedCategory,
                    onSelectedCategoryChanged = { onTriggerEvent(RecipeListEvent.OnSelectCategory(it)) },
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