//
//  ApodDataSource.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 15/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import UIKit
import shared

class ApodDataSource: NSObject, UICollectionViewDataSource {
    
    var apods: [Apod]
    
    override init() {
        apods = []
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.apods.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ApodView", for: indexPath) as! ApodView
        cell.update(apod: apods[indexPath.row])
        return cell

    }
    
    
}
