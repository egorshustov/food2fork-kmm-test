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
    
    private let recipe: Recipe?
    private let dateTimeUtil: DateTimeUtil
    private let messageInfo: GenericMessageInfo?
    private let onTriggerEvent: (RecipeDetailEvent) -> Void
    
    @State var showDialog: Bool
    
    init(
        recipe: Recipe?,
        dateTimeUtil: DateTimeUtil,
        messageInfo: GenericMessageInfo?,
        onTriggerEvent: @escaping (RecipeDetailEvent) -> Void
    ) {
        self.recipe = recipe
        self.dateTimeUtil = dateTimeUtil
        self.messageInfo = messageInfo
        if messageInfo != nil {
            self.showDialog = true
        } else {
            self.showDialog = false
        }
        self.onTriggerEvent = onTriggerEvent
    }
    
    var body: some View {
        NavigationView {
            ScrollView {
                if recipe == nil {
                    Text("We were unable to retrieve the details for this recipe. Try resetting the app.")
                } else {
                    VStack(alignment: .leading) {
                        WebImage(url: URL(string: recipe!.featuredImage))
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
                                DefaultText(
                                    "Updated \(dateTimeUtil.humanizeDatetime(date: recipe!.dateUpdated)) by \(recipe!.publisher)"
                                )
                                .foregroundColor(Color.gray)
                                
                                Spacer()
                                
                                DefaultText(String(recipe!.rating))
                                    .frame(alignment: .trailing)
                            }
                                
                            ForEach(recipe!.ingredients as Array<String>, id: \.self) { ingredient in
                                DefaultText(ingredient)
                                    .padding(.top, 4)
                            }
                        }
                        .background(Color.black)
                        .padding(12)
                    }
                }
            }.navigationBarTitle(Text(recipe?.title ?? "Error"), displayMode: .inline)
                .navigationBarHidden(true)
                .alert(isPresented: $showDialog, content: {
                    return GenericMessageInfoAlert().build(
                        title: messageInfo!.title,
                        onRemoveHeadMessageFromQueue: {
                            onTriggerEvent(RecipeDetailEvent.OnRemoveHeadMessageFromQueue())
                        },
                        description: messageInfo!.description,
                        positiveAction: messageInfo!.positiveAction,
                        negativeAction: messageInfo!.negativeAction
                    )
                })
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .padding()
    }
}
