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
        message: GenericMessageInfo,
        onRemoveHeadMessage: @escaping () -> Void
    ) -> Alert {
        return Alert(
            title: Text(message.title),
            message: Text(message.description ?? "Something went wrong"),
            primaryButton: .default(
                Text(message.positiveAction?.positiveBtnTxt ?? "OK"),
                action: {
                    if (message.positiveAction != nil) {
                        message.positiveAction!.onPositiveAction()
                    }
                    onRemoveHeadMessage()
                }
            ),
            secondaryButton: .default(
                Text(message.negativeAction?.negativeBtnTxt ?? "Cancel"),
                action: {
                    if (message.negativeAction != nil) {
                        message.negativeAction!.onNegativeAction()
                    }
                    onRemoveHeadMessage()
                }
            )
        )
    }
    
}
