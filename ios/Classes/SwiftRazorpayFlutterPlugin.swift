import Flutter
import UIKit
import Razorpay

public class SwiftRazorpayFlutterPlugin: NSObject, FlutterPlugin,RazorpayPaymentCompletionProtocol {
    var resultBack: FlutterResult?
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "razorpay_flutter", binaryMessenger: registrar.messenger())
    let instance = SwiftRazorpayFlutterPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    self.resultBack = result
    
    self.showPaymentForm(call:call)
  }
  
    internal func showPaymentForm(call: FlutterMethodCall){
        var razorpay: Razorpay!

        let arguments=call.arguments as! NSDictionary
        let keyId=arguments["keyId"] as! String
        let txnId=arguments["txnId"] as! String
        let userPhone=arguments["userPhone"] as! String
        let description=arguments["description"] as! String
        let userName=arguments["userName"] as! String
        let amount=arguments["amount"] as! String
        let userEmail=arguments["userEmail"] as! String
        let name=arguments["name"] as! String
        let image=arguments["image"] as! String
        
        razorpay = Razorpay.initWithKey(keyId, andDelegate: self)
        
        let options: [String:Any] = [
            "amount" : "\(Double(amount)!*100)",
            "description": description,
            "image": image,
            "name": name,
            "prefill": [
                "contact": userPhone,
                "email": userEmail
            ],
            "theme": [
                "color": "#007AFF"
            ]
        ]
        razorpay.open(options)
    }
    
    public func onPaymentError(_ code: Int32, description str: String){
        
        
        
        print(str)
        
        var hashMap = [String : Any]()
        
        
        hashMap["status"] = 1
        hashMap["paymentId"] = ""
        hashMap["error"] = true
        hashMap["errorDescription"] = str
        
        
        self.resultBack!(hashMap);
        

    }
    
    public func onPaymentSuccess(_ payment_id: String){
        
        print("Payment Id \(payment_id)")
        
        var hashMap = [String : Any]()
        
        
        hashMap["status"] = 3
        hashMap["paymentId"] = payment_id
        hashMap["error"] = false
        hashMap["errorDescription"] = ""
        
        self.resultBack!(hashMap)

    }
    
}
