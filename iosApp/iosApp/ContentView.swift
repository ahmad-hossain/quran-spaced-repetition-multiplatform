import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Label("Home", systemImage: "house")
                }
            SettingsView()
                .tabItem {
                    Label("Settings", systemImage: "gear")
                }
        }
        .onAppear {
            // Fixes transparent bottom-bar
            if #available(iOS 15.0, *) {
                let appearance = UITabBarAppearance()
                UITabBar.appearance().scrollEdgeAppearance = appearance
            }
        }
    }
}

func getString(_ id: StringResource, args: [Any] = []) -> String {
    return Strings().get(id: id, args: args)
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
