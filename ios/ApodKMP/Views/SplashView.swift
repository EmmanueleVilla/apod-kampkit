//
//  SplashView.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 13/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SplashView: View {
    let interactor: HomeInteractor = HomeInteractor()
    
    init() {
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
            /*
            if(state.homeState.latest[0].explanation != "") {
                self.desc.text = state.homeState.latest[0].explanation
            }
            if(state.homeState.latest[0].url != "") {
                self.image.pin_setImage(from: URL(string: state.homeState.latest[0].url)!)
            }
 */
        })
        interactor.doInit()
    }
    
    var body: some View {
        VStack {
            Image("NasaLogo").resizable().frame(width: UIScreen.logoSize * 1.2, height: UIScreen.logoSize)
            Image("Plus").resizable().frame(width: UIScreen.plusSize, height: UIScreen.plusSize)
            Image("KotlinLogo").resizable().frame(width: UIScreen.logoSize, height: UIScreen.logoSize)
        }
    }
}

struct SplashView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
