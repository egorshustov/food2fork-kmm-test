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
    
    var body: some View {
        VStack {
            HStack {
                Image(systemName: "magnifyingglass")
                TextField(
                    "Search",
                    text: $query,
                    onCommit: {
                        // TODO: execute new search
                    }
                ).onChange(of: query, perform: { newValue in
                    // TODO: update the query
                })
            }.padding(.bottom, 8)
        }.padding(.top, 8)
            .padding(.leading, 8)
            .padding(.trailing, 8)
            .background(Color.white.opacity(0))
    }
}
