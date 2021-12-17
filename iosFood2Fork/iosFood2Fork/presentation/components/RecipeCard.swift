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
        VStack {
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
            
            HStack(alignment: .lastTextBaseline) {
                DefaultText(recipe.title, size: 19)
                    .frame(alignment: .center)
                
                Spacer()
                
                DefaultText(String(recipe.rating))
                    .frame(alignment: .trailing)
            }
            .padding(.top, 8)
            .padding(.leading, 8)
            .padding(.trailing, 8)
            .padding(.bottom, 12)
        }
        .background(Color.black)
        .cornerRadius(8)
        .shadow(radius: 5)
    }
}
