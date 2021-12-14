//
//  RecipeDetailScreen.swift
//  iosFood2Fork
//
//  Created by Egor on 14.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipeDetailScreen: View {
    
    private let networkModule: NetworkModule
    private let cacheModule: CacheModule
    private let getRecipeUseCaseModule: GetRecipeUseCaseModule
    private let recipeId: Int
    private let datetimeUtil = DateTimeUtil()
    
    @ObservedObject var viewModel: RecipeDetailViewModel
    
    init(
        recipeId: Int,
        networkModule: NetworkModule,
        cacheModule: CacheModule
    ) {
        self.recipeId = recipeId
        self.networkModule = networkModule
        self.cacheModule = cacheModule
        self.getRecipeUseCaseModule = GetRecipeUseCaseModule(
            networkModule: self.networkModule,
            cacheModule: self.cacheModule
        )
        viewModel = RecipeDetailViewModel(
            recipeId: self.recipeId,
            getRecipeUseCase: self.getRecipeUseCaseModule.getRecipeUseCase
        )
    }
    
    var body: some View {
        if viewModel.state.recipe != nil {
            RecipeView(recipe: viewModel.state.recipe!, dateTimeUtil: datetimeUtil)
        } else {
            Text("Unable to retrieve the recipe details.")
        }
    }
}
