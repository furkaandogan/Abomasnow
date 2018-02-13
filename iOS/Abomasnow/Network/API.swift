
import Alamofire
import AlamofireObjectMapper

final class API {
    
    static let sharedManager = API()
    
    fileprivate let baseUrl: String = "https://getir-bitaksi-hackathon.herokuapp.com/"
    fileprivate let keyPath = ""
    fileprivate let encoding = URLEncoding.default
    fileprivate let headers: Dictionary<String, String>? = nil

    func searchRecords(_ endpoint: searchRecordsEndpoint, completion: @escaping (SearchRecordsResponse?, Error?) -> Void) {
        request(searchRecords: endpoint)
            .responseObject {
                (response: DataResponse<SearchRecordsResponse>) in
                if response.error != nil {
                    completion(nil, response.error)
                }
                if let topicResponse = response.result.value {
                    completion(topicResponse, nil)
                }
                else {
                    completion(nil, response.result.error)
                }
        }
    }
    
}

extension API {
    
    fileprivate func request(searchRecords endpoint: searchRecordsEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.sharedManager.encoding,
                                 headers: API.sharedManager.headers).validate()
    }
    
    enum searchRecordsEndpoint {
        case all(SearchRecordsRequest)
        
        var path: String {
            switch self {
            case .all:
                return "searchRecords"
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .all:
                return .post
            }
        }
        
        var parameters: [String: Any]? {
            switch self {
            case .all(let searchRecordsRequest):
                return ["startDate" : searchRecordsRequest.startDate, "endDate" : searchRecordsRequest.endDate, "minCount" : searchRecordsRequest.minCount, "maxCount" : searchRecordsRequest.maxCount]
            }
        }
    }
    
}
