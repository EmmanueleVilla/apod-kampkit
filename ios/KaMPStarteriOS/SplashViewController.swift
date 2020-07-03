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
    let image = UIImageView()
    let desc = UILabel()
    let manager = SDWebImageManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.cyan
        view.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(image)
        image.translatesAutoresizingMaskIntoConstraints = false
        let topImage = image.topAnchor.constraint(equalTo: view.topAnchor)
        let leftImage = image.leftAnchor.constraint(equalTo: view.leftAnchor)
        let rightImage = image.rightAnchor.constraint(equalTo: view.rightAnchor)
        let heightImage = image.heightAnchor.constraint(equalToConstant: 250)
        NSLayoutConstraint.activate([topImage, leftImage, rightImage, heightImage])
        
        view.addSubview(desc)
        desc.translatesAutoresizingMaskIntoConstraints = false
        let topDesc = desc.topAnchor.constraint(equalTo: view.bottomAnchor)
        let leftDesc = image.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 12)
        let rightDesc = image.rightAnchor.constraint(equalTo: view.rightAnchor, constant: 12)
        NSLayoutConstraint.activate([topDesc, leftDesc, rightDesc])
       
        interactor.subscribe(callback: { state in
            self.desc.text = state.splashState.apod.explanation
            self.manager.loadImage(with: URL(string: state.splashState.apod.url), options: SDWebImageOptions(), progress: { i, j, url in }, completed: { a,b,c,d,e,f  in })
        })
        interactor.doInit()
    }
    
}
