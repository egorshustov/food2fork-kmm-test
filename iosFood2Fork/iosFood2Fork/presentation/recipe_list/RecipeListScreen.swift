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
    }
    
    var body: some View {
        Text("RecipeListScreen")
    }
}
