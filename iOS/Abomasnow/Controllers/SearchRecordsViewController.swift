//
//  SearchRecordsViewController.swift
//  Abomasnow
//
//  Created by Hakan Eren on 11/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class SearchRecordsViewController: UIViewController {    
    
    @IBOutlet weak var recordView: RecordView!
    var searchRecordsRequest = SearchRecordsRequest(startDate: "2016-01-26", endDate: "2017-02-02", minCount: 2700, maxCount: 3000)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        recordView.recordsViewController = self
        recordView.searchRecordsRequest = searchRecordsRequest
    }

    @IBAction func filter(_ sender: UIBarButtonItem) {
        if let viewController = storyboard?.instantiateViewController(withIdentifier: "FilterTableViewController") as? FilterTableViewController {
            viewController.delegate = self
            viewController.searchRecordsRequest = searchRecordsRequest
            self.navigationController?.pushViewController(viewController, animated: true)
        }

    }
}

extension SearchRecordsViewController: FilterDelegate {
    
    func filter(updated searchRecordsRequest: SearchRecordsRequest) {
        recordView.searchRecordsRequest = searchRecordsRequest
    }
    
}
