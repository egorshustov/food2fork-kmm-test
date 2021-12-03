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
}
