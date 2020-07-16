//
//  ContentView.swift
//  apodmac
//
//  Created by Emmanuele Villa on 15/07/2020.
//  Copyright © 2020 Emmanuele Villa. All rights reserved.
//

import SwiftUI
import shared

class ApodObservable: ObservableObject {
    @Published var titleText = "Loading..."
}

struct ContentView: View {
    
    @ObservedObject var apod = ApodObservable()
    
    let interactor = HomeInteractor()
    init() {
        let me = self
        interactor.subscribe(callback: { state in
            
            if(state.homeState.latest.count == 0) {
                return
            }
            
            me.apod.titleText = state.homeState.latest[0].title
        })
        interactor.doInit()
    }
    
    
    var body: some View {
        VStack {
            Text(apod.titleText)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            Button(
                action: { self.apod.titleText = "CLICK" },
              label: { Text("Click") }
            )
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
