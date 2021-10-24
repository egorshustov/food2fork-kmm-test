package com.egorshustov.food2forkkmm.android.presentation.recipe_list.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egorshustov.food2forkkmm.android.presentation.components.RECIPE_IMAGE_BOX_HEIGHT
import com.egorshustov.food2forkkmm.domain.model.Recipe

@Composable
fun RecipeList(
    isLoading: Boolean,
    recipes: List<Recipe>,
    onClickRecipeListItem: (Int) -> Unit
) {
    when {
        isLoading && recipes.isEmpty() -> LoadingRecipeListShimmer(imageHeight = RECIPE_IMAGE_BOX_HEIGHT.dp)

        recipes.isEmpty() -> { /* Nothing to show... No recipes */
        }

        else -> {
            LazyColumn {
                itemsIndexed(items = recipes) { index, recipe ->
                    RecipeCard(
                        recipe = recipe,
                        onClick = { onClickRecipeListItem(recipe.id) }
                    )
                }
            }
        }
    }
}