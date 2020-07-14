//
//  AppDelegate.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let navigationController = UINavigationController();
        window = UIWindow(frame: UIScreen.main.bounds);
        window!.rootViewController = navigationController
        window!.makeKeyAndVisible();
        navigationController.setViewControllers([SplashViewController()], animated: false)
        return true
    }
}




