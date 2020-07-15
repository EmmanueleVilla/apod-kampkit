//
//  HomeViewController.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 14/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//
import shared
import Foundation
import UIKit
import PINRemoteImage
import SnapKit

class HomeViewController: UIViewController {
    let interactor: HomeInteractor = HomeInteractor()
    let highlightImage = UIImageView()
    let highlightTitle = UILabel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.white
        
        view.addSubview(highlightImage)
        view.addSubview(highlightTitle)
        
        highlightImage.snp.makeConstraints { make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.top.equalToSuperview()
            make.height.equalTo(500)
        }
        
        highlightTitle.snp.makeConstraints { make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.bottom.equalTo(highlightImage.snp.bottom).offset(UIScreen.margin)
        }
        
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
            
            self.highlightTitle.text = state.homeState.latest[0].title
            self.highlightImage.pin_setImage(from: URL(string: state.homeState.latest[0].imageUrl)!)
            
            self.view.layoutIfNeeded()
        })
        
        interactor.doInit()
    }
}
