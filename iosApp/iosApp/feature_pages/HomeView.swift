//
//  HomeView.swift
//  iosApp
//
//  Created by Ahmad Hossain on 10/9/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import KMPObservableViewModelSwiftUI
import shared
import SwiftUI

struct HomeView: View {
    @StateViewModel var viewModel = Constants.dependencies.pagesViewModel
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

    private var showDialogBinding: Binding<Bool> {
        Binding(
            get: { viewModel.stateValue.isGradeDialogVisible },
            set: { _ in
                viewModel.onEvent(event: PagesEvent.GradeDialogDismissed())
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
            .customConfirmDialog(isPresented: showDialogBinding, actions: {
                Text(getString(strings.grade_your_review))
                    .font(.callout.weight(.semibold))
                    .frame(maxWidth: .infinity)
                    .foregroundColor(Color(UIColor.gray))
                Text(String(viewModel.stateValue.lastClickedPageNumber))
                    .font(.callout)
                    .frame(maxWidth: .infinity)
                    .foregroundColor(Color(UIColor.gray))
                Spacer().frame(height: 16)
                Divider()
                let gradeOptions = GradeOption.companion.gradeOptions
                let gradeClickAction: (Int32) -> Void = {
                    viewModel.onEvent(event: PagesEvent.GradeSelected(grade: $0))
                    viewModel.onEvent(event: PagesEvent.GradeDialogConfirmed())
                }
                ForEach(gradeOptions, id: \.grade) { gradeOption in
                    let emoji = gradeOption.emoji
                    let textRes = gradeOption.textRes

                    if gradeOption.grade == gradeOptions.last!.grade {
                        GradeButton(emoji: emoji, textRes: textRes) { gradeClickAction(gradeOption.grade) }
                    } else {
                        GradeItem(emoji: emoji, textRes: textRes) { gradeClickAction(gradeOption.grade) }
                    }
                }
            })
            .navigationBarTitle(getString(strings.revision), displayMode: .inline)
        }
    }

    func PagesList(pages: [Page]) -> some View {
        List {
            Section {
                ForEach(pages, id: \.pageNumber) { page in
                    HStack(spacing: 0) {
                        Text("")
                        PageItem(page: page) {
                            viewModel.onEvent(event: PagesEvent.PageClicked(page: page))
                        }
                        .disabled(!page.shouldGradePage)
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

    func GradeItem(emoji: String, textRes: StringResource, onItemClicked: @escaping () -> Void) -> some View {
        Group {
            GradeButton(emoji: emoji, textRes: textRes, onButtonClicked: onItemClicked)
            Divider()
        }
        .padding(.vertical, 4)
    }

    func GradeButton(emoji: String, textRes: StringResource, onButtonClicked: @escaping () -> Void) -> some View {
        Button(
            action: onButtonClicked,
            label: {
                Text("\(emoji) " + getString(textRes))
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
        )
    }
}

extension View {
    @ViewBuilder
    func modify(@ViewBuilder _ transform: (Self) -> (some View)?) -> some View {
        if let view = transform(self), !(view is EmptyView) {
            view
        } else {
            self
        }
    }

    func customConfirmDialog<A: View>(isPresented: Binding<Bool>, @ViewBuilder actions: @escaping () -> A) -> some View {
        return modifier(CustomConfirmDialogModifier(isPresented: isPresented, actions: actions))
    }
}

struct CustomConfirmDialogModifier<A>: ViewModifier where A: View {
    @Binding var isPresented: Bool
    @ViewBuilder let actions: () -> A

    func body(content: Content) -> some View {
        ZStack {
            content
                .frame(maxWidth: .infinity, maxHeight: .infinity)

            ZStack(alignment: .bottom) {
                if isPresented {
                    Color.primary.opacity(0.2)
                        .ignoresSafeArea()
                        .onTapGesture {
                            isPresented = false
                        }
                        .transition(.opacity)
                }

                if isPresented {
                    VStack {
                        GroupBox {
                            actions()
                                .frame(maxWidth: .infinity, alignment: .leading)
                        }
                        .clipShape(.rect(cornerRadius: 16))
                        GroupBox {
                            let cancelButtonLabel = Text("Cancel")
                                .fontWeight(.bold)
                                .frame(maxWidth: .infinity, alignment: .center)
                            let cancelAction = { isPresented = false }
                            if #available(iOS 15.0, *) {
                                Button(
                                    role: .cancel,
                                    action: cancelAction,
                                    label: { cancelButtonLabel }
                                )
                            } else {
                                Button(
                                    action: cancelAction,
                                    label: { cancelButtonLabel }
                                )
                            }
                        }
                        .clipShape(.rect(cornerRadius: 16))
                    }
                    .font(.title3)
                    .padding(8)
                    .transition(.move(edge: .bottom))
                }
            }
        }
        .animation(.easeInOut, value: isPresented)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
