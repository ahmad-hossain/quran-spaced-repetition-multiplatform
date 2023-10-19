//
//  TableCell.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/14/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct TableCell: View {
    let text: String

    init(_ text: String) {
        self.text = text
    }

    var body: some View {
        Text(text).frame(minWidth: 0, maxWidth: .infinity)
    }
}
