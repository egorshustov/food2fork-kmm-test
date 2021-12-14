//
//  RecipeView.swift
//  iosFood2Fork
//
//  Created by Egor on 14.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import SDWebImageSwiftUI

struct RecipeView: View {
    
    private let recipe: Recipe
    private let dateTimeUtil: DateTimeUtil
    
    init(recipe: Recipe, dateTimeUtil: DateTimeUtil) {
        self.recipe = recipe
        self.dateTimeUtil = dateTimeUtil
    }
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                WebImage(url: URL(string: recipe.featuredImage))
                    .resizable() // 1
                    .placeholder {
                        Rectangle().foregroundColor(.white)
                    }
                    .indicator(.activity)
                    .transition(.fade(duration: 0.5))
                    .scaledToFill() // 2
                    .frame(height: 250, alignment: .center) // 3
                    .clipped() // 4
                
                VStack(alignment: .leading) {
                    HStack(alignment: .lastTextBaseline) {
                        Text(
                            "Updated \(dateTimeUtil.humanizeDatetime(date: recipe.dateUpdated)) by \(recipe.publisher)"
                        )
                        .foregroundColor(Color.gray)
                        
                        Spacer()
                        
                        Text(String(recipe.rating))
                            .frame(alignment: .trailing)
                    }
                        
                    ForEach(recipe.ingredients as Array<String>, id: \.self) { ingredient in
                        Text(ingredient)
                            .padding(.top, 4)
                    }
                }
                .background(Color.black)
                .padding(12)
            }
            .navigationBarTitle(Text(recipe.title), displayMode: .inline)
        }
    }
}
