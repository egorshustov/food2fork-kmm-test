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
                removeHeadMessageFromQueue()
            default:
                self.appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder()
                    .id(id: UUID().uuidString)
                    .title(title: "Error")
                    .uiComponentType(uiComponentType: UIComponentType.Dialog())
                    .description(description: "Unknown Event")
                    .positive(positiveAction: PositiveAction(positiveBtnTxt: "OK", onPositiveAction: {})))
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
                            self.appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder()
                                .id(id: "GetRecipe.Error")
                                .title(title: "Error")
                                .uiComponentType(uiComponentType: UIComponentType.Dialog())
                                .description(description: exception.message ?? "Unknown Error")
                                .positive(positiveAction: PositiveAction(positiveBtnTxt: "OK", onPositiveAction: {})))
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
    
    private func appendToMessageQueue(messageInfoBuilder: GenericMessageInfo.Builder) {
        let currentState = self.state.copy() as! RecipeDetailState
        let queue = currentState.queue
        let queueUtil = GenericMessageInfoQueueUtil()
        let messageInfo = messageInfoBuilder.build()
        if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: messageInfo) {
            queue.add(element: messageInfo)
            updateState(queue: queue)
        }
    }
    
    private func removeHeadMessageFromQueue() {
        let currentState = self.state.copy() as! RecipeDetailState
        let queue = currentState.queue
        do {
            try queue.remove()
            updateState(queue: queue)
        }
        catch {
            print("\(error)")
        }
    }
    
}
