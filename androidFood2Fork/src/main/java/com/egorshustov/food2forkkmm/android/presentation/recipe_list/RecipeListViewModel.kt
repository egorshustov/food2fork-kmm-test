package com.egorshustov.food2forkkmm.android.presentation.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.food2forkkmm.domain.util.Result
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.Queue
import com.egorshustov.food2forkkmm.presentation.model.UIComponentType
import com.egorshustov.food2forkkmm.presentation.recipe_list.FoodCategory
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListEvent
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListState
import com.egorshustov.food2forkkmm.presentation.util.GenericMessageInfoQueueUtil
import com.egorshustov.food2forkkmm.usecases.recipe_list.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, // don't need for this VM
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    val state: MutableState<RecipeListState> = mutableStateOf(RecipeListState())

    init {
        onTriggerEvent(RecipeListEvent.LoadRecipes)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        when (event) {
            RecipeListEvent.LoadRecipes -> loadRecipes()
            RecipeListEvent.NextPage -> nextPage()
            RecipeListEvent.NewSearch -> newSearch()
            is RecipeListEvent.OnUpdateQuery -> state.value =
                state.value.copy(query = event.query, selectedCategory = null)
            is RecipeListEvent.OnSelectCategory -> onSelectCategory(event.category)
            RecipeListEvent.OnRemoveHeadMessageFromQueue -> removeHeadMessageFromQueue()
            else -> appendToMessageQueue(
                GenericMessageInfo.Builder()
                    .id(UUID.randomUUID().toString())
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description("Unknown Event")
            )
        }
    }

    private fun loadRecipes() = with(state.value) {
        searchRecipesUseCase(
            page = page,
            query = query
        ).onEach { result ->
            state.value = when (result) {
                is Result.Success -> copy(recipes = recipes + result.data, isLoading = false)

                is Result.Error -> {
                    appendToMessageQueue(
                        GenericMessageInfo.Builder()
                            .id("SearchRecipes.Error")
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

    private fun nextPage() = with(state.value) {
        state.value = copy(page = page + 1)
        loadRecipes()
    }

    private fun newSearch() = with(state.value) {
        state.value = copy(page = 1, recipes = emptyList())
        loadRecipes()
    }

    private fun onSelectCategory(category: FoodCategory) {
        state.value = state.value.copy(selectedCategory = category, query = category.value)
        newSearch()
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