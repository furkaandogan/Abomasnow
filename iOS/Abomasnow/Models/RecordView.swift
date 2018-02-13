//
//  RecordView.swift
//  Abomasnow
//
//  Created by Hakan Eren on 11/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class RecordView: NSObject {

    weak var recordsViewController: UIViewController!
    @IBOutlet weak var recordsTableView: UITableView!
    @IBOutlet weak var loadingActivityIndicatorView: UIActivityIndicatorView!
    var records = [Record]()
    var searchRecordsRequest = SearchRecordsRequest() { didSet { sendRequest() } }
    
    fileprivate func sendRequest() {
        DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
            self.records.removeAll()
            DispatchQueue.main.async {
                self.recordsTableView.reloadData()
                self.recordsTableView.isHidden = true
                self.loadingActivityIndicatorView.isHidden = false
            }
        }
        API.sharedManager.searchRecords(.all(searchRecordsRequest)) {
            (searchRecordsResponse: SearchRecordsResponse?, error: Error?) in
            DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                if let searchRecordsResponse = searchRecordsResponse {
                    self.records.append(contentsOf: searchRecordsResponse.records)
                }
                DispatchQueue.main.async {
                    self.recordsTableView.reloadData()
                    self.recordsTableView.isHidden = false
                    self.loadingActivityIndicatorView.isHidden = true
                }
            }
        }
    }
}

extension RecordView: UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return records.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        
        let record = records[indexPath.item]
        
        if let recordIdentifier = record.identifier?.identifier{
            cell.textLabel?.text = recordIdentifier
        }
        else {
            cell.textLabel?.text = ""
        }
        
        cell.detailTextLabel?.text = "\(record.totalCount)"
        
        return cell
    }
    
}
