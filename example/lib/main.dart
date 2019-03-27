import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:razorpay_flutter/razorpay_flutter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String txnId = "1234";
    String userPhone = "9023074222";
    String userEmail = "mr.dishantmahajan@gmail.com";
    String userName = "Dishant Mahajan";
    String description = "Sample Testing";
    String amount = "700";
    String keyId = "rzp_test_q243jF11JQLXH0";
    String name = "Bike Shop";
    String image =
        "http://content.nike.com/content/dam/one-nike/globalAssets/social_media_images/nike_swoosh_logo_black.png";

    Map<dynamic, dynamic> platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await RazorpayFlutter.startPayment(txnId, userPhone,
          userEmail, userName, description, amount, name, image, keyId);
    } on PlatformException {
      print("Failed");
//      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
//      _platformVersion = platformVersion;
      print(platformVersion);
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
