//
//  Record.swift
//  Abomasnow
//
//  Created by Hakan Eren on 11/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

struct SearchRecordsRequest: Equatable {
    
    var startDate = ""
    var endDate = ""
    var minCount = 0
    var maxCount = 0
    
    static func ==(a: SearchRecordsRequest, b: SearchRecordsRequest) -> Bool {
        return (a.startDate == b.startDate && a.endDate == b.endDate && a.minCount == b.minCount && a.maxCount == b.maxCount)
    }
    
    static func !=(a: SearchRecordsRequest, b: SearchRecordsRequest) -> Bool {
        return !(a==b)
    }

}

import ObjectMapper
import ObjectMapper_Realm
import RealmSwift

class SearchRecordsResponse: Object, Mappable {
    
    @objc dynamic var code = 0
    @objc dynamic var msg = ""
    var records = List<Record>()
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        code    <- map["code"]
        msg     <- map["msg"]
        records <- (map["records"], ListTransform<Record>())
    }
    
}

class Record: Object, Mappable {
    
    @objc dynamic var identifier: Identifier?
    @objc dynamic var totalCount = 0
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier  <- map["_id"]
        totalCount  <- map["totalCount"]
    }
    
}

class Identifier: Object, Mappable {
    
    @objc dynamic var identifier = ""
    @objc dynamic var key = ""
    @objc dynamic var value = ""
    @objc dynamic var createdAt = ""

    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier  <- map["_id"]
        key         <- map["key"]
        value       <- map["value"]
        createdAt   <- map["createdAt"]
    }
    
}
