//
//  ApodCollectionFlowLayout.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 16/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import UIKit

class ApodCollectionFlowLayout: NSObject, UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: UIScreen.apodImageSize, height: UIScreen.apodImageSize)
    }
}
