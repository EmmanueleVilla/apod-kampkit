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
import Hero

class HomeViewController: UIViewController {
    
    let interactor: HomeInteractor = HomeInteractor()
    let highlightImage = UIImageView()
    let highlightTitle = UILabel()
    let latestTitle = UILabel()
    var latestCollection: UICollectionView?
    let latestFlow = UICollectionViewFlowLayout()
    let latestDataSource = ApodDataSource()
    let latestDelegate = ApodCollectionFlowLayout()
    
    var highlightApod: Apod? = nil
    
    @objc func handleHighlightTap(sender: UITapGestureRecognizer) {
        if sender.state == .ended {
            let detail = ApodDetailViewController()
            detail.apod = self.highlightApod
            navigationController?.pushViewController(detail, animated: true)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.hero.isEnabled = true
        
        latestCollection = UICollectionView(frame: CGRect.zero, collectionViewLayout: latestFlow)
        view.backgroundColor = UIColor.white
        
        view.addSubview(highlightImage)
        view.addSubview(highlightTitle)
        view.addSubview(latestTitle)
        view.addSubview(latestCollection!)
        
        highlightImage.hero.id = "apod-image"
        highlightImage.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(handleHighlightTap)))
        highlightImage.isUserInteractionEnabled = true
        highlightImage.snp.makeConstraints { make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.top.equalToSuperview()
            make.height.equalTo(UIScreen.screenHeight / 2)
        }
        
        highlightTitle.textColor = UIColor.white
        highlightTitle.font = highlightTitle.font.withSize(30)
        highlightTitle.numberOfLines = 3
        highlightTitle.snp.makeConstraints { make in
            make.leading.equalToSuperview().offset(UIScreen.margin)
            make.trailing.equalToSuperview().offset(UIScreen.margin)
            make.top.equalTo(highlightImage.snp.top).offset(UIDevice.current.hasNotch ? UIScreen.margin * 3 : UIScreen.margin)
        }
        
        latestTitle.text = "Latest:"
        latestTitle.font = latestTitle.font.withSize(20)
        latestTitle.textColor = UIColor.darkGray
        latestTitle.snp.makeConstraints{ make in
            make.leading.equalToSuperview().offset(UIScreen.margin)
            make.trailing.equalToSuperview()
            make.top.equalTo(highlightImage.snp.bottom).offset(UIScreen.margin + UIScreen.apodMargin)
        }
        
        latestFlow.scrollDirection = .horizontal
        latestCollection!.backgroundColor = UIColor.white
        latestCollection!.register(ApodView.self, forCellWithReuseIdentifier: "ApodView")
        latestCollection!.dataSource = latestDataSource
        latestCollection!.delegate = latestDelegate
        latestCollection!.snp.makeConstraints{ make in
            make.leading.equalToSuperview()
            make.trailing.equalToSuperview()
            make.top.equalTo(latestTitle.snp.bottom).offset(UIScreen.margin)
            make.height.equalTo(UIScreen.apodImageSize)
        }
        
        interactor.subscribe(callback: { state in
            if(state.homeState.latest.count == 0) {
                return
            }
            
            self.highlightApod = state.homeState.latest[0]
            self.highlightTitle.text = state.homeState.latest[0].title
            self.highlightImage.pin_setImage(from: URL(string: state.homeState.latest[0].imageUrl)!, completion: { (result)  in
                guard let image = result.image else { return }
                let imageWidth: CGFloat = image.size.width
                let imageHeight: CGFloat = image.size.height
                let ratio =  imageWidth / imageHeight
                let newHeight = self.highlightImage.frame.width / ratio
                
                UIView.animate(withDuration: 0.5) {
                    self.highlightImage.snp.updateConstraints { make in
                      make.height.equalTo(newHeight)
                  }
                    self.highlightImage.superview!.layoutIfNeeded()
                }
            })
            
            self.latestDataSource.apods = Array(state.homeState.latest[1...state.homeState.latest.count - 2])
            self.latestCollection!.reloadData()
            
            self.view.layoutIfNeeded()
        })
        
        interactor.doInit()
    }
}
