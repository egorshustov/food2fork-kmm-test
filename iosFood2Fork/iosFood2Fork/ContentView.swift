import SwiftUI
import shared

struct ContentView: View {
    let hello = "hello, ios developer"

	var body: some View {
		Text(hello)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
