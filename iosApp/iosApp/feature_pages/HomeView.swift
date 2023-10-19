//
//  HomeView.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/9/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import KMMViewModelSwiftUI
import shared
import SwiftUI

struct HomeView: View {
    @StateViewModel var viewModel = PagesViewModel(dataSource: Constants.dependencies.pageDataSource)
    let strings = SharedRes.strings()

    var body: some View {
        NavigationView {
            List {
                Section {
                    // TODO: add selection for TODAY and ALL pages
                    ForEach(viewModel.stateValue.allPages, id: \.pageNumber) { page in
                        HStack(spacing: 0) {
                            Text("")
                            PageItem(page: page)
                        }
                    }
                }
                header: {
                    HStack {
                        TableCell(getString(strings.page_number_abbrev))
                        TableCell(getString(strings.interval))
                        TableCell(getString(strings.repetitions))
                        TableCell(getString(strings.due_date))
                    }
                }
            }
            .listStyle(.plain)
            .padding(.horizontal, -20)
            .navigationBarTitle(getString(strings.revision))
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
