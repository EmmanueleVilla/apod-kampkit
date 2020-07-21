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
import Hero

class ApodDetailViewController: UIViewController {
    
    var apod: Apod? = nil
    var height = UIScreen.screenHeight / 2
    let apodImage = UIImageView()
    let apodTitle = UILabel()
    let apodDescription = UILabel()
    let scrollView = UIScrollView()
    let wrapper = UIView()
    
    override func viewDidAppear(_ animated: Bool) {
        apodImage.pin_setImage(from: URL(string: apod!.imageUrl)!, completion: { (result)  in
            guard let image = result.image else { return }
            let imageWidth: CGFloat = image.size.width
            let imageHeight: CGFloat = image.size.height
            let ratio =  imageWidth / imageHeight
            let newHeight = self.apodImage.frame.width / ratio
            
            UIView.animate(withDuration: 0.5) {
                self.apodImage.snp.updateConstraints { make in
                  make.height.equalTo(newHeight)
              }
                self.apodImage.superview!.layoutIfNeeded()
            }
        })
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.addSubview(scrollView)
        scrollView.addSubview(wrapper)
        
        view.backgroundColor = UIColor.white
        
        wrapper.addSubview(apodImage)
        wrapper.addSubview(apodTitle)
        wrapper.addSubview(apodDescription)
        
        scrollView.snp.makeConstraints { make in
            make.top.equalToSuperview()
            make.bottom.equalToSuperview()
            make.width.equalToSuperview()
        }
        
        wrapper.snp.makeConstraints { make in
            make.edges.equalToSuperview()
            make.width.equalToSuperview()
        }
        
        self.hero.isEnabled = true
        apodImage.hero.id = "apod-image"
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
        
        apodDescription.text = apod!.explanation
        apodDescription.font = apodDescription.font.withSize(16)
        apodDescription.textColor = UIColor.darkGray
        apodDescription.numberOfLines = 0
        apodDescription.snp.makeConstraints{ make in
            make.leading.equalToSuperview().offset(UIScreen.margin)
            make.trailing.equalToSuperview()
            make.top.equalTo(apodTitle.snp.bottom).offset(UIScreen.margin)
        }
    }
}
