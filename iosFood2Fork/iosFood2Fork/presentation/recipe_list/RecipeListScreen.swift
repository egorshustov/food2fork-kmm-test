//
//  RecipeListScreen.swift
//  iosFood2Fork
//
//  Created by Egor on 02.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipeListScreen: View {
    // dependencies
    private let networkModule: NetworkModule
    private let cacheModule: CacheModule
    private let searchRecipesUseCaseModule: SearchRecipesUseCaseModule
    
    @ObservedObject var viewModel: RecipeListViewModel
    
    init (
        networkModule: NetworkModule,
        cacheModule: CacheModule
    ) {
        self.networkModule = networkModule
        self.cacheModule = cacheModule
        self.searchRecipesUseCaseModule = SearchRecipesUseCaseModule(
            networkModule: self.networkModule,
            cacheModule: self.cacheModule
        )
        self.viewModel = RecipeListViewModel(
            searchRecipesUseCase: searchRecipesUseCaseModule.searchRecipesUseCase, foodCategoryUtil: FoodCategoryUtil()
        )
    }
    
    var body: some View {
        VStack {
            List {
                ForEach(viewModel.state.recipes, id: \.self.id) { recipe in
                    Text(recipe.title)
                }
            }
        }
    }
}
