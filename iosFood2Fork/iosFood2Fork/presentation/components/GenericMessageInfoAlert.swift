//
//  GenericMessageInfoAlert.swift
//  iosFood2Fork
//
//  Created by Egor on 18.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct GenericMessageInfoAlert {
    
    func build(
        title: String,
        onRemoveHeadMessageFromQueue: @escaping () -> Void,
        description: String? = nil,
        positiveAction: PositiveAction? = nil,
        negativeAction: NegativeAction? = nil
    ) -> Alert {
        return Alert(
            title: Text(title),
            message: Text(description ?? "Something went wrong"),
            primaryButton: .default(
                Text(positiveAction?.positiveBtnTxt ?? "OK"),
                action: {
                    if (positiveAction != nil) {
                        positiveAction!.onPositiveAction()
                    }
                    onRemoveHeadMessageFromQueue()
                }
            ),
            secondaryButton: .default(
                Text(negativeAction?.negativeBtnTxt ?? "Cancel"),
                action: {
                    if (negativeAction != nil) {
                        negativeAction!.onNegativeAction()
                    }
                    onRemoveHeadMessageFromQueue()
                }
            )
        )
    }
    
}
