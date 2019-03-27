import 'dart:async';

import 'package:flutter/services.dart';

class RazorpayFlutter {
  static const MethodChannel _channel = const MethodChannel('razorpay_flutter');

  static Future<Map<dynamic, dynamic>> startPayment(
      String txnId,
      String userPhone,
      String userEmail,
      String userName,
      String description,
      String amount,
      String name,
      String image,
      String keyId) async {
    final Map<dynamic, dynamic> result =
        await _channel.invokeMethod('showPaymentView', <String, dynamic>{
      // data to be passed to the function
      'option': 3,
      'txnId': txnId,
      'userPhone': userPhone,
      'name': name,
      'image': image,
      'userEmail': userEmail,
      'userName': userName,
      'description': description,
      'keyId': keyId,
      'amount': amount
    });
    return result;
  }
}
