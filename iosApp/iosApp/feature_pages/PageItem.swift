
//  PageItem.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/14/23.
//

import shared
import SwiftUI

struct PageItem: View {
    var page: Page
    var onClick: () -> Void

    var body: some View {
        Button(
            action: { self.onClick() },
            label: {
                HStack {
                    TableCell(String(page.pageNumber))
                    TableCell(String(page.interval))
                    TableCell(String(page.repetitions))
                    let relativeDueDate = page.relativeDueDate
                    TableCell(relativeDueDate != nil ? relativeDueDate! : getString(SharedRes.strings().na))
                }
            }
        )
        .foregroundColor(.primary)
    }
}
