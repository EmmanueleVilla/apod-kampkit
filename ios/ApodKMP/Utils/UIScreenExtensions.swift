//
//  UIScreenExtensions.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 14/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import SwiftUI

extension UIScreen {
   static let screenWidth = UIScreen.main.bounds.size.width
    static let screenHeight = UIScreen.main.bounds.size.height
    static let screenSize = UIScreen.main.bounds.size
    static let logoSize: CGFloat = screenHeight / 100 * 18
    static let plusSize: CGFloat = screenHeight / 100 * 4
    static let margin: CGFloat = 12
}
