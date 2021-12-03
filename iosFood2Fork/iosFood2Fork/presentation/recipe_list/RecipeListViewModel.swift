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
        // TODO: perform a search"
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
        let currentState: RecipeListState = self.state.copy() as! RecipeListState
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            page: Int32(page ?? Int(currentState.page)),
            query: query ?? currentState.query,
            selectedCategory: currentState.selectedCategory,
            recipes: currentState.recipes,
            queue: queue ?? currentState.queue)
    }
}
