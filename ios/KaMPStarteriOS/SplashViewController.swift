//
//  ViewController.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared
import PINRemoteImage

class SplashViewController: UIViewController {
    
    let interactor: SplashInteractor = SplashInteractor()
    let image = UIImageView()
    let desc = UILabel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.cyan
        view.addSubview(image)
        
        image.translatesAutoresizingMaskIntoConstraints = false
        image.backgroundColor = UIColor.yellow
        let topImage = image.topAnchor.constraint(equalTo: view.topAnchor)
        let leftImage = image.leftAnchor.constraint(equalTo: view.leftAnchor)
        let rightImage = image.rightAnchor.constraint(equalTo: view.rightAnchor)
        let heightImage = image.heightAnchor.constraint(equalToConstant: 250)
        NSLayoutConstraint.activate([topImage, leftImage, rightImage, heightImage])
        
        view.addSubview(desc)
        desc.textColor = UIColor.purple
        desc.backgroundColor = UIColor.green
        desc.translatesAutoresizingMaskIntoConstraints = false
        let topDesc = desc.topAnchor.constraint(equalTo: image.bottomAnchor)
        let leftDesc = image.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 12)
        let rightDesc = image.rightAnchor.constraint(equalTo: view.rightAnchor, constant: 12)
        NSLayoutConstraint.activate([topDesc, leftDesc, rightDesc])
       
        interactor.subscribe(callback: { state in
            if(state.splashState.apod.explanation != "") {
                self.desc.text = state.splashState.apod.explanation
            }
            if(state.splashState.apod.url != "") {
                self.image.pin_setImage(from: URL(string: state.splashState.apod.url)!)
            }
        })
        interactor.doInit()
    }
    
}
