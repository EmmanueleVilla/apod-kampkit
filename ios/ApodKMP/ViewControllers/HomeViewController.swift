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
    let latest = UICollectionView(frame: CGRect.zero, collectionViewLayout: UICollectionViewFlowLayout())
    let dataSource = ApodDataSource()
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.white
        
        view.addSubview(highlightImage)
        view.addSubview(highlightTitle)
        view.addSubview(latest)
        
        highlightImage.snp.makeConstraints { make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.top.equalToSuperview()
            make.height.equalTo(500)
        }
        
        highlightTitle.textColor = UIColor.white
        highlightTitle.snp.makeConstraints { make in
            make.leading.equalToSuperview().offset(UIScreen.margin)
            make.trailing.equalToSuperview()
            make.top.equalTo(highlightImage.snp.top).offset(UIScreen.margin)
            make.height.equalTo(150)
        }
        
        latest.backgroundColor = UIColor.yellow
        latest.register(ApodView.self, forCellWithReuseIdentifier: "ApodView")
        latest.dataSource = dataSource
        latest.snp.makeConstraints{ make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.top.equalTo(highlightImage.snp.bottom).offset(UIScreen.margin)
            make.height.equalTo(250)
        }
        
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
            
            self.highlightTitle.text = state.homeState.latest[0].title
            self.highlightImage.pin_setImage(from: URL(string: state.homeState.latest[0].imageUrl)!)
            
            self.dataSource.apods = state.homeState.latest
            self.latest.reloadData()
            
            self.view.layoutIfNeeded()
        })
        
        interactor.doInit()
    }
}
