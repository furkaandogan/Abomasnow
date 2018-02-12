//
//  FilterTableViewController.swift
//  Abomasnow
//
//  Created by Hakan Eren on 11/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

protocol FilterDelegate {
    func filter(updated searchRecordsRequest: SearchRecordsRequest)
}

class FilterTableViewController: UITableViewController {

    var delegate: FilterDelegate?
    var searchRecordsRequest: SearchRecordsRequest?
    
    @IBOutlet weak var startDatePicker: UIDatePicker!
    @IBOutlet weak var endDatePicker: UIDatePicker!
    @IBOutlet weak var minCountTextField: UITextField!
    @IBOutlet weak var maxCountTextField: UITextField!
    
    @IBAction func done(_ sender: UIBarButtonItem) {
        if Int(maxCountTextField.text!)! > Int(minCountTextField.text!)! {
            if endDatePicker.date > startDatePicker.date  {
                updateSearchRecords()
                navigationController?.popViewController(animated: true)
            }
            else {
                errorAlert(message: NSLocalizedString("\"End Date\" must be greater than \"Start Date\"", comment: "startDate >= endDate error!"))
            }
        }
        else {
            errorAlert(message: NSLocalizedString("\"Maximum Count\" must be greater than \"Minimum Count\"", comment: "minCount >= maxCount error!"))
        }
    }
    
    private var dateFormatter: DateFormatter {
        get {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            return dateFormatter
        }
    }
    
    private func updateSearchRecords() {
        let startDate = dateFormatter.string(from: startDatePicker.date)
        let endDate = dateFormatter.string(from: endDatePicker.date)
        let minCount = Int(minCountTextField.text!)!
        let maxCount = Int(maxCountTextField.text!)!
        let filteredSearchRecordsRequest = SearchRecordsRequest(startDate: startDate, endDate: endDate, minCount: minCount, maxCount: maxCount)
        if searchRecordsRequest != filteredSearchRecordsRequest {
            self.delegate?.filter(updated: filteredSearchRecordsRequest)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureSearchRecords()
    }
    
    private func configureSearchRecords() {
        if let searchRecordsRequest = searchRecordsRequest {
            minCountTextField.text = "\(searchRecordsRequest.minCount)"
            maxCountTextField.text = "\(searchRecordsRequest.maxCount)"

            if let startDate = dateFormatter.date(from: searchRecordsRequest.startDate) {
                startDatePicker.setDate(startDate, animated: false)
            }
            if let endDate = dateFormatter.date(from: searchRecordsRequest.endDate) {
                endDatePicker.setDate(endDate, animated: false)
            }
        }
    }

}

extension FilterTableViewController: UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        if string == " " {
            return true
        }
        let invertedLetterCharacterSet = NSCharacterSet.letters.inverted
        let letterFilteredString = (string.components(separatedBy: invertedLetterCharacterSet)).joined(separator: "")
        return string != letterFilteredString
    }
    
}

extension UIViewController {
    
    func errorAlert(message: String) {
        let alertView = UIAlertController(title: NSLocalizedString("Ooops!", comment: "Surprise reaction"), message: message, preferredStyle: .alert)
        alertView.addAction(UIAlertAction(title: NSLocalizedString("Oki", comment: "Dismiss an alert"), style: .cancel, handler: nil))
        self.present(alertView, animated: true, completion: nil)
    }
    
}
