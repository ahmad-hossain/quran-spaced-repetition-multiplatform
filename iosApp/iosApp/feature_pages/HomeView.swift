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
    private var selectedTabBinding: Binding<Int> {
        Binding(
            get: { Int(viewModel.stateValue.selectedTab.ordinal) },
            set: {
                viewModel.onEvent(event: PagesEvent.TabClicked(
                    tab: UiTabs.values().get(index: Int32($0))!)
                )
            }
        )
    }
    
    var body: some View {
        NavigationView {
            VStack {
                Picker("", selection: selectedTabBinding) {
                    Text(getString(strings.today)).tag(0)
                    Text(getString(strings.all)).tag(1)
                }
                .pickerStyle(.segmented)
                PagesList(pages: viewModel.stateValue.displayedPages)
            }
            .navigationBarTitle(getString(strings.revision), displayMode: .inline)
        }
    }

    func PagesList(pages: [Page]) -> some View {
        List {
            Section {
                ForEach(pages, id: \.pageNumber) { page in
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
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
