//
//  SplashView.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 13/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import UIKit
import shared
import PINRemoteImage
import SnapKit

class SplashViewController: UIViewController {
    
    let interactor: HomeInteractor = HomeInteractor()
    let nasaLogo = UIImageView()
    let add = UIImageView()
    let kotlinLogo = UIImageView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.white
        
        view.addSubview(nasaLogo)
        view.addSubview(add)
        view.addSubview(kotlinLogo)
        
        var marginTop : CGFloat = UIScreen.screenHeight - UIScreen.logoSize - UIScreen.logoSize
        marginTop = marginTop - UIScreen.plusSize - UIScreen.margin - UIScreen.margin
        marginTop = marginTop / 2
        
        nasaLogo.image = UIImage(named: "NasaLogo")
        nasaLogo.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalToSuperview().offset(marginTop)
            make.width.equalTo(UIScreen.logoSize * 1.2)
            make.height.equalTo(UIScreen.logoSize)
        }
        
        add.image = UIImage(named: "Plus")
        add.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalTo(nasaLogo.snp.bottom).offset(UIScreen.margin)
            make.width.equalTo(UIScreen.plusSize)
            make.height.equalTo(UIScreen.plusSize)
        }
        
        kotlinLogo.image = UIImage(named: "KotlinLogo")
        kotlinLogo.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalTo(add.snp.bottom).offset(UIScreen.margin)
            make.width.equalTo(UIScreen.logoSize)
            make.height.equalTo(UIScreen.logoSize)
        }
       
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
            if(state.homeState.latest[0].explanation != "") {
                //self.desc.text = state.homeState.latest[0].explanation
            }
            if(state.homeState.latest[0].url != "") {
                //self.image.pin_setImage(from: URL(string: state.homeState.latest[0].url)!)
            }
        })
        interactor.doInit()
    }
}
