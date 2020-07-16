//
//  ApodView.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 15/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import UIKit
import shared

class ApodView: UICollectionViewCell {
    
    let image = UIImageView()
    
    override init(frame: CGRect) {
        super.init(frame:frame)
        
        self.addSubview(self.image)
        self.image.contentMode = .scaleAspectFill
        self.image.clipsToBounds = true
        self.image.snp.makeConstraints { make in
            make.edges.equalToSuperview().offset(UIScreen.apodMargin)
        }
    }
    
    func update(apod: Apod) {
        self.image.pin_setImage(from: URL(string: apod.imageUrl)!)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
}
