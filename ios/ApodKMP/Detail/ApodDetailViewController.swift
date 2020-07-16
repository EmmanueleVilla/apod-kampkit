//
//  ApodDetailViewController.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 16/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import UIKit
import shared

class ApodDetailViewController: UIViewController {
    
    var apod: Apod? = nil
    var height = UIScreen.screenHeight / 2
    let apodImage = UIImageView()
    let apodTitle = UILabel()
    let apodDescription = UILabel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.white
        
        view.addSubview(apodImage)
        view.addSubview(apodTitle)
        view.addSubview(apodDescription)
        
        apodImage.snp.makeConstraints { make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.top.equalToSuperview()
            make.height.equalTo(UIScreen.screenHeight / 2)
        }
        
        apodTitle.text = apod!.title
        apodTitle.font = apodTitle.font.withSize(20)
        apodTitle.textColor = UIColor.darkGray
        apodTitle.snp.makeConstraints{ make in
            make.leading.equalToSuperview().offset(UIScreen.margin)
            make.trailing.equalToSuperview()
            make.top.equalTo(apodImage.snp.bottom).offset(UIScreen.margin)
        }
        
        apodDescription.text = apod!.title
        apodDescription.font = apodDescription.font.withSize(18)
        apodDescription.textColor = UIColor.darkGray
        apodDescription.snp.makeConstraints{ make in
            make.leading.equalToSuperview().offset(UIScreen.margin)
            make.trailing.equalToSuperview()
            make.top.equalTo(apodTitle.snp.bottom).offset(UIScreen.margin)
        }
    }
}
