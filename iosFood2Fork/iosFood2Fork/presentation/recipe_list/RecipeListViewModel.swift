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
    let searchRecipesUseCase: SearchRecipesUseCase
    let foodCategoryUtil: FoodCategoryUtil
    
    @Published var state: RecipeListState = RecipeListState()
    
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
                doNothing()
            case is RecipeListEvent.NewSearch:
                doNothing()
            case is RecipeListEvent.OnUpdateQuery:
                doNothing()
            case is RecipeListEvent.OnSelectCategory:
                doNothing()
            case is RecipeListEvent.OnRemoveHeadMessageFromQueue:
                doNothing()
            default:
                doNothing()
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
        
    private func doNothing() {
        // does nothing
    }
    
    private func appendRecipes(recipes: [Recipe]) {
        let currentState = self.state.copy() as! RecipeListState
        var currentRecipes = currentState.recipes
        currentRecipes.append(contentsOf: recipes)
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: currentState.page,
            query: currentState.query,
            selectedCategory: currentState.selectedCategory,
            recipes: currentRecipes,
            queue: currentState.queue
        )
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
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        let currentState = self.state.copy() as! RecipeListState
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            page: Int32(page ?? Int(currentState.page)),
            query: query ?? currentState.query,
            selectedCategory: currentState.selectedCategory,
            recipes: currentState.recipes,
            queue: queue ?? currentState.queue)
    }
}
