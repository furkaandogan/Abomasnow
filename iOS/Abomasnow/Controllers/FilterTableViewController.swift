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
    @IBOutlet weak var minCountPickerView: UIPickerView!
    @IBOutlet weak var maxCountPickerView: UIPickerView!
    
    fileprivate let minPickerValue = 2700
    fileprivate let maxPickerValue = 3001
    
    @IBAction func done(_ sender: UIBarButtonItem) {
        if maxCountPickerView.selectedRow(inComponent: 0) > minCountPickerView.selectedRow(inComponent: 0) {
            if endDatePicker.date > startDatePicker.date  {
                updateFilter()
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
    
    private func updateFilter() {
        let startDate = dateFormatter.string(from: startDatePicker.date)
        let endDate = dateFormatter.string(from: endDatePicker.date)
        let minCount = minCountPickerView.selectedRow(inComponent: 0) + minPickerValue
        let maxCount = maxCountPickerView.selectedRow(inComponent: 0) + minPickerValue
        let filteredSearchRecordsRequest = SearchRecordsRequest(startDate: startDate, endDate: endDate, minCount: minCount, maxCount: maxCount)
        if searchRecordsRequest != filteredSearchRecordsRequest {
            self.delegate?.filter(updated: filteredSearchRecordsRequest)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configurePickers()
    }
    
    private func configurePickers() {
        if let searchRecordsRequest = searchRecordsRequest {
            minCountPickerView.selectRow(searchRecordsRequest.minCount - minPickerValue, inComponent: 0, animated: false)
            maxCountPickerView.selectRow(searchRecordsRequest.maxCount - minPickerValue, inComponent: 0, animated: false)
            
            if let startDate = dateFormatter.date(from: searchRecordsRequest.startDate) {
                startDatePicker.setDate(startDate, animated: false)
            }
            if let endDate = dateFormatter.date(from: searchRecordsRequest.endDate) {
                endDatePicker.setDate(endDate, animated: false)
            }
        }
    }

}

extension FilterTableViewController: UIPickerViewDataSource {
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        if pickerView == minCountPickerView || pickerView == maxCountPickerView{
            return 1
        }
        return 0
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView == minCountPickerView || pickerView == maxCountPickerView{
            return maxPickerValue - minPickerValue
        }
        return 0
    }
    
}

extension FilterTableViewController: UIPickerViewDelegate {
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView == minCountPickerView || pickerView == maxCountPickerView{
            return "\(minPickerValue + row)"
        }
        return ""
    }
    
}

extension UIViewController {
    
    func errorAlert(message: String) {
        let alertView = UIAlertController(title: NSLocalizedString("Ooops!", comment: "Surprise reaction"), message: message, preferredStyle: .alert)
        alertView.addAction(UIAlertAction(title: NSLocalizedString("Oki", comment: "Dismiss an alert"), style: .cancel, handler: nil))
        self.present(alertView, animated: true, completion: nil)
    }
    
}
