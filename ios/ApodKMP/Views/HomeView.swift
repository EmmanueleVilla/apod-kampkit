//
//  HomeView.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 14/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct HomeView: View {
    let interactor: HomeInteractor = HomeInteractor()
    
    init() {
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
        })
        interactor.doInit()
    }
    
    var body: some View {
        VStack {
            Image("NasaLogo").resizable().frame(width: UIScreen.screenWidth, height: 500)
        }
    }
}
