//
//  RecipeDetailViewModel.swift
//  iosFood2Fork
//
//  Created by Egor on 14.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

import SwiftUI
import shared

class RecipeDetailViewModel: ObservableObject {
    
    // Dependencies
    private let getRecipeUseCase: GetRecipeUseCase
    
    @Published var state: RecipeDetailState = RecipeDetailState()
    
    init(
        recipeId: Int,
        getRecipeUseCase: GetRecipeUseCase
    ) {
        self.getRecipeUseCase = getRecipeUseCase
        onTriggerEvent(event: RecipeDetailEvent.GetRecipe(recipeId: Int32(recipeId)))
    }
    
    func onTriggerEvent(event: RecipeDetailEvent) {
        switch event {
            case is RecipeDetailEvent.GetRecipe:
                getRecipe(recipeId: Int((event as! RecipeDetailEvent.GetRecipe).recipeId))
            case is RecipeDetailEvent.OnRemoveHeadMessageFromQueue:
                doNothing()
            default:
                doNothing()
        }
    }
    
    private func getRecipe(recipeId: Int){
        getRecipeUseCase.invoke(
            id: Int32(recipeId)
        ).collectCommon(
            coroutineScope: nil,
            callback: { result in
                switch result {
                        case is ResultSuccess<AnyObject>: do {
                            let resultSuccess = result as! ResultSuccess<AnyObject>
                            let recipe = resultSuccess.data as! Recipe
                            self.updateState(isLoading: false, recipe: recipe)
                        }
                        case is Result.Error: do {
                            let resultError = result as! Result.Error
                            let exception = resultError.exception
                            print(exception)
                            self.updateState(isLoading: false)
                        }
                        case is Result.Loading: do {
                            print("Loading")
                            self.updateState(isLoading: true)
                        }
                        default: do {
                            print("default")
                        }
                }
            }
        )
    }
    
    private func updateState(
            isLoading: Bool? = nil,
            recipe: Recipe? = nil,
            queue: Queue<GenericMessageInfo>? = nil
        ){
            let currentState = (self.state.copy() as! RecipeDetailState)
            self.state = self.state.doCopy(
                isLoading: isLoading ?? currentState.isLoading,
                recipe: recipe ?? currentState.recipe,
                queue: queue ?? currentState.queue
            )
        }
    
    private func doNothing() {}
}
