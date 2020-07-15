//
//  ContentView.swift
//  apodmac
//
//  Created by Emmanuele Villa on 15/07/2020.
//  Copyright © 2020 Emmanuele Villa. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    
    let interactor = HomeInteractor()
    init() {
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
        })
        interactor.doInit()
    }
    var body: some View {
        Text("Hello, World!")
            .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
