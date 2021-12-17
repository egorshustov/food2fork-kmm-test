//
//  DefaultText.swift
//  iosFood2Fork
//
//  Created by Egor on 17.12.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct DefaultText: View {
    
    private let text: String
    private let size: CGFloat
    
    init(_ text: String, size: CGFloat = 15) {
        self.text = text
        self.size = size
    }
    
    var body: some View {
        Text(text).font(Font.custom("Avenir", size: size))
    }
}
