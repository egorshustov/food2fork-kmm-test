//
//  RecipeCard.swift
//  iosFood2Fork
//
//  Created by Egor on 11.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import SDWebImageSwiftUI

struct RecipeCard: View {
    
    private let recipe: Recipe
    
    init(recipe: Recipe) {
        self.recipe = recipe
    }
    
    var body: some View {
        WebImage(url: URL(string: recipe.featuredImage))
            .resizable() // 1
            .placeholder(Image(systemName: "photo"))
            .placeholder {
                Rectangle().foregroundColor(.white)
            }
            .indicator(.activity)
            .transition(.fade(duration: 0.5))
            .scaledToFill() // 2
            .frame(height: 250, alignment: .center) // 3
            .clipped() // 4
    }
}
