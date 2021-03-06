package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.food2forkkmm.android.util.RECIPE_ID_ARG
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.Result
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.PositiveAction
import com.egorshustov.food2forkkmm.presentation.model.Queue
import com.egorshustov.food2forkkmm.presentation.model.UIComponentType
import com.egorshustov.food2forkkmm.presentation.recipe_detail.RecipeDetailEvent
import com.egorshustov.food2forkkmm.presentation.recipe_detail.RecipeDetailState
import com.egorshustov.food2forkkmm.presentation.util.GenericMessageInfoQueueUtil
import com.egorshustov.food2forkkmm.usecases.recipe_detail.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

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
            RecipeDetailEvent.OnRemoveHeadMessageFromQueue -> removeHeadMessageFromQueue()
            else -> appendToMessageQueue(
                GenericMessageInfo.Builder()
                    .id(UUID.randomUUID().toString())
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description("Unknown Event")
                    .positive(PositiveAction("OK"))
            )
        }
    }

    private fun getRecipe(id: Int) = with(state.value) {
        getRecipeUseCase(id).collectCommon(viewModelScope) { result: Result ->
            state.value = when (result) {
                is Result.Success<*> -> copy(recipe = result.data as? Recipe, isLoading = false)

                is Result.Error -> {
                    appendToMessageQueue(
                        GenericMessageInfo.Builder()
                            .id("GetRecipe.Error")
                            .title("Error")
                            .uiComponentType(UIComponentType.Dialog)
                            .description(result.exception.message ?: "Unknown Error")
                            .positive(PositiveAction("OK"))
                    )
                    copy(isLoading = false)
                }

                Result.Loading -> copy(isLoading = true)
            }
        }
    }

    private fun removeHeadMessageFromQueue() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            // nothing to remove, queue is empty
        }
    }

    private fun appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder) {
        val queue = state.value.queue
        val messageInfo = messageInfoBuilder.build()
        if (!GenericMessageInfoQueueUtil.doesMessageAlreadyExistInQueue(queue, messageInfo)) {
            queue.add(messageInfo)
        }
        state.value = state.value.copy(queue = queue)
    }
}