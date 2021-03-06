//
//  RecipeListViewModel.swift
//  iosFood2Fork
//
//  Created by Egor on 03.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeListViewModel: ObservableObject {
    
    // Dependencies
    private let searchRecipesUseCase: SearchRecipesUseCase
    private let foodCategoryUtil: FoodCategoryUtil
    
    @Published var state: RecipeListState = RecipeListState()
    
    @Published var showDialog: Bool = false
    
    init(
        searchRecipesUseCase: SearchRecipesUseCase,
        foodCategoryUtil: FoodCategoryUtil
    ) {
        self.searchRecipesUseCase = searchRecipesUseCase
        self.foodCategoryUtil = foodCategoryUtil
        onTriggerEvent(event: RecipeListEvent.LoadRecipes())
    }
    
    func onTriggerEvent(event: RecipeListEvent) {
        switch event {
            case is RecipeListEvent.LoadRecipes:
                loadRecipes()
            case is RecipeListEvent.NextPage:
                nextPage()
            case is RecipeListEvent.NewSearch:
                newSearch()
            case is RecipeListEvent.OnUpdateQuery:
                onUpdateQuery(query: (event as! RecipeListEvent.OnUpdateQuery).query)
            case is RecipeListEvent.OnSelectCategory:
                onUpdateSelectedCategory(foodCategory: (event as! RecipeListEvent.OnSelectCategory).category)
            case is RecipeListEvent.OnRemoveHeadMessageFromQueue:
                removeHeadMessageFromQueue()
            default:
                self.appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder()
                    .id(id: UUID().uuidString)
                    .title(title: "Error")
                    .uiComponentType(uiComponentType: UIComponentType.Dialog())
                    .description(description: "Unknown Event")
                    .positive(positiveAction: PositiveAction(positiveBtnTxt: "OK", onPositiveAction: {})))
        }
    }
    
    private func loadRecipes() {
        let currentState = self.state.copy() as! RecipeListState
        searchRecipesUseCase.invoke(
            page: currentState.page,
            query: currentState.query
        ).collectCommon(
            coroutineScope: nil,
            callback: { result in
                switch result {
                    case is ResultSuccess<AnyObject>: do {
                        self.checkThread()
                        let resultSuccess = result as! ResultSuccess<AnyObject>
                        let recipeList = resultSuccess.data as! [Recipe]
                        if !recipeList.isEmpty {
                            self.appendRecipes(recipes: recipeList)
                        }
                        self.updateState(isLoading: false)
                    }
                    case is Result.Error: do {
                        let resultError = result as! Result.Error
                        let exception = resultError.exception
                        print(exception)
                        self.appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder()
                            .id(id: "SearchRecipes.Error")
                            .title(title: "Error")
                            .uiComponentType(uiComponentType: UIComponentType.Dialog())
                            .description(description: exception.message ?? "Unknown Error")
                            .positive(positiveAction: PositiveAction(positiveBtnTxt: "OK", onPositiveAction: {})))
                        self.updateState(isLoading: false)
                    }
                    case is Result.Loading: do {
                        print("Loading")
                        self.updateState(isLoading: true)
                    }
                    default: do {
                        print("default")
                    }
                }
            }
        )
    }
    
    private func checkThread() {
        let currentThread = Thread.current
        print("currentThread: \(currentThread)")
        let isMainThread = Thread.isMainThread
        print("isMainThread: \(isMainThread)")
    }
    
    private func nextPage() {
        let currentState = self.state.copy() as! RecipeListState
        updateState(page: Int(currentState.page) + 1)
        loadRecipes()
    }
    
    private func newSearch() {
        let currentState = self.state.copy() as! RecipeListState
        var foodCategory = currentState.selectedCategory
        if (foodCategory?.value != currentState.query) {
            foodCategory = nil
        }
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: 1, // reset
            query: currentState.query,
            selectedCategory: foodCategory,
            recipes: [], // reset
            bottomRecipe: currentState.bottomRecipe,
            queue: currentState.queue)
    
        loadRecipes()
    }
    
    private func onUpdateQuery(query: String) {
        updateState(query: query)
    }
    
    private func onUpdateSelectedCategory(foodCategory: FoodCategory?) {
        let currentState = self.state.copy() as! RecipeListState
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: currentState.page,
            query: currentState.query,
            selectedCategory: foodCategory,
            recipes: currentState.recipes,
            bottomRecipe: currentState.bottomRecipe,
            queue: currentState.queue)
    
        onUpdateQuery(query: foodCategory?.value ?? "")
        onTriggerEvent(event: RecipeListEvent.NewSearch())
    }
    
    private func appendRecipes(recipes: [Recipe]) {
        var currentState = self.state.copy() as! RecipeListState
        var currentRecipes = currentState.recipes
        currentRecipes.append(contentsOf: recipes)
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: currentState.page,
            query: currentState.query,
            selectedCategory: currentState.selectedCategory,
            recipes: currentRecipes,
            bottomRecipe: currentState.bottomRecipe,
            queue: currentState.queue
        )
        currentState = self.state.copy() as! RecipeListState
        updateState(bottomRecipe: currentState.recipes[currentState.recipes.count - 1])
    }
    
    func shouldQueryNextPage(recipe: Recipe) -> Bool {
        // check if looking at the bottom recipe
        // if lookingAtBottom -> proceed
        // if PAGE_SIZE * page <= recipes.length
        // if !queryInProgress
        // else -> do nothing
        let currentState = self.state.copy() as! RecipeListState
        if (recipe.id == currentState.bottomRecipe?.id) {
            if (RecipeListState.Companion().RECIPE_PAGINATION_PAGE_SIZE * currentState.page <= currentState.recipes.count) {
                if (!currentState.isLoading) {
                    return true
                }
            }
        }
        return false
    }
    
    /**
     *  Not everything can be conveniently updated with this function.
     *  Things like recipes and selectedCategory must have their own functions.
     *  Basically if more then one action must be taken then it cannot be updated with this function.
     *  ex: updating selected category requires us to 1) update category, 2) update the query, 3) trigger new search event
     */
    func updateState(
        isLoading: Bool? = nil,
        page: Int? = nil,
        query: String? = nil,
        bottomRecipe: Recipe? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        let currentState = self.state.copy() as! RecipeListState
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            page: Int32(page ?? Int(currentState.page)),
            query: query ?? currentState.query,
            selectedCategory: currentState.selectedCategory,
            recipes: currentState.recipes,
            bottomRecipe: bottomRecipe ?? currentState.bottomRecipe,
            queue: queue ?? currentState.queue
        )
        shouldShowDialog()
    }
    
    private func shouldShowDialog() {
        let currentState = self.state.copy() as! RecipeListState
        showDialog = currentState.queue.items.count > 0
    }
    
    private func appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder) {
        let currentState = self.state.copy() as! RecipeListState
        let queue = currentState.queue
        let queueUtil = GenericMessageInfoQueueUtil()
        let messageInfo = messageInfoBuilder.build()
        if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: messageInfo) {
            queue.add(element: messageInfo)
            updateState(queue: queue)
        }
    }
    
    private func removeHeadMessageFromQueue() {
        let currentState = self.state.copy() as! RecipeListState
        let queue = currentState.queue
        do {
            try queue.remove()
            updateState(queue: queue)
        }
        catch {
            print("\(error)")
        }
    }
}
