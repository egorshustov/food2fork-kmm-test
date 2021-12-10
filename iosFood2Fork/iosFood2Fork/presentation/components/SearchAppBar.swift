//
//  SearchAppBar.swift
//  iosFood2Fork
//
//  Created by Egor on 09.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SearchAppBar: View {
    
    @State var query: String = ""
    private let selectedCategory: FoodCategory?
    private let foodCategories: [FoodCategory]
    private let onTriggerEvent: (RecipeListEvent) -> Void
    
    init(
        query: String,
        selectedCategory: FoodCategory?,
        foodCategories: [FoodCategory],
        onTriggerEvent: @escaping (RecipeListEvent) -> Void
    ) {
        self.onTriggerEvent = onTriggerEvent
        self.selectedCategory = selectedCategory
        self.foodCategories = foodCategories
        self._query = State(initialValue: query)
    }
    
    var body: some View {
        VStack {
            HStack {
                Image(systemName: "magnifyingglass")
                TextField(
                    "Search",
                    text: $query,
                    onCommit: {
                        onTriggerEvent(RecipeListEvent.NewSearch())
                    }
                ).onChange(of: query, perform: { newValue in
                    onTriggerEvent(RecipeListEvent.OnUpdateQuery(query: newValue))
                })
            }.padding(.bottom, 8)
            ScrollView(.horizontal) {
                HStack(spacing: 10) {
                    ForEach(foodCategories, id: \.self) { foodCategory in
                        FoodCategoryChip(
                            category: foodCategory.value,
                            isSelected: selectedCategory == foodCategory
                        ).onTapGesture {
                            query = foodCategory.value
                            onTriggerEvent(RecipeListEvent.OnSelectCategory(category: foodCategory))
                        }
                    }
                }
            }
        }.padding(.top, 8)
            .padding(.leading, 8)
            .padding(.trailing, 8)
            .background(Color.white.opacity(0))
    }
}
