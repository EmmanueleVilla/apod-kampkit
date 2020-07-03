//
//  ViewController.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared
import SDWebImage

class SplashViewController: UIViewController {
    
    let interactor: SplashInteractor = SplashInteractor()
    let image = UIImage()
    let desc = UILabel()
    let manager = SDWebImageManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.cyan
        interactor.subscribe(callback: { state in
            self.desc.text = state.splashState.apod.explanation
            self.manager.loadImage(with: URL(string: state.splashState.apod.url), options: SDWebImageOptions(), progress: { i, j, url in }, completed: { a,b,c,d,e,f  in })
        })
        interactor.doInit()
    }
    
}
