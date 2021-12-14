//
//  RecipeDetailViewModel.swift
//  iosFood2Fork
//
//  Created by Egor on 14.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

import SwiftUI
import shared

class RecipeDetailViewModel: ObservableObject {
    
    // Dependencies
    private let getRecipeUseCase: GetRecipeUseCase
    
    @State var state: RecipeDetailState = RecipeDetailState()
    
    init(
        recipeId: Int,
        getRecipeUseCase: GetRecipeUseCase
    ) {
        self.getRecipeUseCase = getRecipeUseCase
        // TODO: Get the recipe from the cache
    }
}
