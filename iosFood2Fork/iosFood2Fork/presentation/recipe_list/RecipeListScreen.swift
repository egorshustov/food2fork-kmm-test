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
            HStack {
                Text("Page: \(viewModel.state.page), Size: \(viewModel.state.recipes.count)").padding()
            }
            SearchAppBar(
                query: viewModel.state.query,
                onTriggerEvent: viewModel.onTriggerEvent
            )
            List {
                ForEach(viewModel.state.recipes, id: \.self.id) { recipe in
                    Text(recipe.title)
                        .onAppear {
                            if viewModel.shouldQueryNextPage(recipe: recipe) {
                                viewModel.onTriggerEvent(event: RecipeListEvent.NextPage())
                            }
                        }
                }
            }
        }
    }
}
