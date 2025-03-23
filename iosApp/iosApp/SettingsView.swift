//
//  SettingsView.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/9/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import KMPObservableViewModelSwiftUI
import shared
import SwiftUI

struct SettingsView: View {
    @Environment(\.colorScheme) var colorScheme
    @StateViewModel var viewModel = SettingsViewModel(
        settingsRepo: Constants.dependencies.settingsRepository,
        changePageRangeUseCase: ChangePageRange(
            settingsRepo: Constants.dependencies.settingsRepository,
            pageRepo: Constants.dependencies.pageDataSource
        )
    )
    private var notificationDateBinding: Binding<Date> {
        Binding(
            get: { viewModel.stateValue.userPreferences.notificationTime.toDate() },
            set: {
                viewModel.onEvent(event: SettingsEvent.TimePickerTimeChanged(time: DateUtilKt.toLocalTime($0)))
            }
        )
    }

    let strings = SharedRes.strings()

    var body: some View {
        NavigationView {
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
                            .accentColor(.primary)
                            .padding(.horizontal, 11)
                            .padding(.vertical, 8)
                            .background(Color(buttonBgColor).opacity(buttonBgOpacity))
                            .cornerRadius(7)
                            .buttonStyle(.borderless)
                    }
                    .padding(.vertical, -1)
                }

                Section(header: Text(getString(strings.notifications))) {
                    DatePicker(
                        getString(strings.notification_time_setting),
                        selection: notificationDateBinding,
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
            .navigationBarTitle(getString(strings.settings), displayMode: .inline)
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
