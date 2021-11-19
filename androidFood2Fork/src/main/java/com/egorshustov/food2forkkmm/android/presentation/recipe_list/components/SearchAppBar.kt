package com.egorshustov.food2forkkmm.android.presentation.recipe_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.egorshustov.food2forkkmm.presentation.recipe_list.FoodCategory
import com.egorshustov.food2forkkmm.presentation.recipe_list.FoodCategoryUtil

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    query: String,
    categories: List<FoodCategory>,
    selectedCategory: FoodCategory? = null,
    onSelectedCategoryChanged: (FoodCategory) -> Unit,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = query,
                    onValueChange = onQueryChanged,
                    label = { Text(text = "Search...") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onExecuteSearch()
                            keyboardController?.hide()
                        }
                    ),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
                )
            }
            LazyRow(modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)) {
                items(categories) { itemCategory ->
                    FoodCategoryChip(
                        foodCategory = itemCategory.value,
                        isSelected = selectedCategory == itemCategory,
                        onSelectedCategoryChanged = { value ->
                            FoodCategoryUtil.getFoodCategory(value)?.let { newCategory ->
                                onSelectedCategoryChanged(newCategory)
                            }
                        }
                    )
                }
            }
        }
    }
}