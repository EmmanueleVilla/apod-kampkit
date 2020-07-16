//
//  UIDeviceExtensions.swift
//  ApodKMP
//
//  Created by Emmanuele Villa on 16/07/2020.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import UIKit

extension UIDevice {
    var hasNotch: Bool {
        let bottom = UIApplication.shared.windows.filter {$0.isKeyWindow}.first!.safeAreaInsets.bottom
        return bottom > 0
    }
}
