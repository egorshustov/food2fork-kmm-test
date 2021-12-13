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
    private let foodCategories: [FoodCategory]
    
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
        let foodCategoryUtil = FoodCategoryUtil()
        self.viewModel = RecipeListViewModel(
            searchRecipesUseCase: searchRecipesUseCaseModule.searchRecipesUseCase, foodCategoryUtil: foodCategoryUtil
        )
        self.foodCategories = foodCategoryUtil.getAllFoodCategories()
    }
    
    var body: some View {
        NavigationView {
            VStack {
                SearchAppBar(
                    query: viewModel.state.query,
                    selectedCategory: viewModel.state.selectedCategory,
                    foodCategories: foodCategories,
                    onTriggerEvent: viewModel.onTriggerEvent
                )
                List {
                    ForEach(viewModel.state.recipes, id: \.self.id) { recipe in
                        NavigationLink {
                            Text("\(recipe.title)")
                        } label: {
                            RecipeCard(recipe: recipe)
                                .onAppear {
                                    if viewModel.shouldQueryNextPage(recipe: recipe) {
                                        viewModel.onTriggerEvent(event: RecipeListEvent.NextPage())
                                    }
                                }
                        }
                        .listRowInsets(EdgeInsets())
                        .padding(.top, 10)
                    }
                }
                .listStyle(PlainListStyle())
            }
            .navigationBarTitle("Return to main screen")
            .navigationBarHidden(true)
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .padding()
    }
}
