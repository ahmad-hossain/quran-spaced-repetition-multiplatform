//
//  SettingsView.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/9/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import KMMViewModelSwiftUI
import shared
import SwiftUI

struct SettingsView: View {
    @Environment(\.colorScheme) var colorScheme
    @StateViewModel var viewModel: SettingsViewModel
    @State var notificationDate: Date
    let strings: SharedRes.strings

    init() {
        self.strings = SharedRes.strings()
        let deps = Constants.dependencies
        _viewModel = StateViewModel(
            wrappedValue: SettingsViewModel(
                settingsRepo: deps.settingsRepository,
                changePageRangeUseCase: ChangePageRange(
                    settingsRepo: deps.settingsRepository,
                    pageRepo: deps.pageDataSource
                )
            )
        )
        let date = _viewModel.wrappedValue.stateValue.userPreferences.notificationTime.toDate()
        _notificationDate = State(initialValue: date)
    }

    var body: some View {
        Form {
            let prefs = viewModel.stateValue.userPreferences

            Section(header: Text(getString(strings.revision))) {
                HStack {
                    Text(getString(strings.quran_pages_setting))
                    Spacer()
                    let buttonBgColor = colorScheme == .dark ? UIColor.systemGray4 : UIColor.systemGray5
                    let buttonBgOpacity = colorScheme == .dark ? 1 : 0.6
                    // TODO: handle on-click
                    Button("\(prefs.startPage) - \(prefs.endPage)") {}
                        .accentColor(colorScheme == .dark ? .white : .black)
                        .padding(.horizontal, 11)
                        .padding(.vertical, 8)
                        .background(Color(buttonBgColor).opacity(buttonBgOpacity))
                        .cornerRadius(7)
                        .buttonStyle(.borderless)
                }
            }

            // TODO: Send new time to VM
            Section(header: Text(getString(strings.notifications))) {
                DatePicker(
                    getString(strings.notification_time_setting),
                    selection: $notificationDate,
                    displayedComponents: .hourAndMinute
                )
            }

            Section(
                header: Text(getString(strings.backup)),
                footer: Text(getString(strings.backup_footer))
            ) {
                Button(
                    action: {},
                    label: {
                        Label(
                            getString(strings.headline_export_data),
                            systemImage: "square.and.arrow.up"
                        )
                    }
                )
                Button(
                    action: {},
                    label: {
                        Label(
                            getString(strings.headline_import_data),
                            systemImage: "square.and.arrow.down"
                        )
                    }
                )
            }
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
