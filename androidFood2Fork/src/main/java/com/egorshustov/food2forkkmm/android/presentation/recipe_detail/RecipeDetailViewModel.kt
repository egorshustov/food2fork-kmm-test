package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.food2forkkmm.android.util.RECIPE_ID_ARG
import com.egorshustov.food2forkkmm.domain.util.Result
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.UIComponentType
import com.egorshustov.food2forkkmm.presentation.recipe_detail.RecipeDetailEvent
import com.egorshustov.food2forkkmm.presentation.recipe_detail.RecipeDetailState
import com.egorshustov.food2forkkmm.usecases.recipe_detail.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipeUseCase
) : ViewModel() {

    val state: MutableState<RecipeDetailState> = mutableStateOf(RecipeDetailState())

    init {
        savedStateHandle.get<Int>(RECIPE_ID_ARG)?.let { recipeId ->
            onTriggerEvent(RecipeDetailEvent.GetRecipe(recipeId))
        }
    }

    fun onTriggerEvent(event: RecipeDetailEvent) {
        when (event) {
            is RecipeDetailEvent.GetRecipe -> getRecipe(event.recipeId)
            else -> appendToMessageQueue(
                GenericMessageInfo.Builder()
                    .id(UUID.randomUUID().toString())
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description("Unknown Event")
            )
        }
    }

    private fun getRecipe(id: Int) = with(state.value) {
        getRecipeUseCase(id).onEach { result ->
            state.value = when (result) {
                is Result.Success -> copy(recipe = result.data, isLoading = false)

                is Result.Error -> {
                    appendToMessageQueue(
                        GenericMessageInfo.Builder()
                            .id("GetRecipe.Error")
                            .title("Error")
                            .uiComponentType(UIComponentType.Dialog)
                            .description(result.exception.message ?: "Unknown Error")
                    )
                    copy(isLoading = false)
                }

                Result.Loading -> copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder) {
        val queue = state.value.queue
        queue.add(messageInfoBuilder.build())
        state.value = state.value.copy(queue = queue)
    }
}