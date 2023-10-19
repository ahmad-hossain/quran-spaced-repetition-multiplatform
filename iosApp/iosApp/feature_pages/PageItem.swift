
//  PageItem.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/14/23.
//

import shared
import SwiftUI

struct PageItem: View {
    var page: Page

    var body: some View {
        Button(
            action: {},
            label: {
                HStack {
                    TableCell(String(page.pageNumber))
                    TableCell(String(page.interval))
                    TableCell(String(page.repetitions))
                    TableCell(page.dueDate != nil ? "\(page.dueDate!)" : getString(SharedRes.strings().na))
                }
            }
        )
        .foregroundColor(.primary)
    }
}
