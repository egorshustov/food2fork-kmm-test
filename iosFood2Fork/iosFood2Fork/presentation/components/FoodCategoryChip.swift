//
//  FoodCategoryChip.swift
//  iosFood2Fork
//
//  Created by Egor on 10.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct FoodCategoryChip: View {
    
    private let category: String
    private let isSelected: Bool
    
    init(category: String, isSelected: Bool = false) {
        self.category = category
        self.isSelected = isSelected
    }
    
    var body: some View {
        HStack {
            DefaultText(category, size: 16) // TODO: update font
                .padding(8)
                .background(isSelected ? Color.gray : Color.blue) // TODO: update gray color
                .foregroundColor(Color.white)
        }
        .cornerRadius(10)
    }
    
    struct FoodCategoryChip_Previews: PreviewProvider {
        static var previews: some View {
            FoodCategoryChip(category: "Chicken")
        }
    }
}
