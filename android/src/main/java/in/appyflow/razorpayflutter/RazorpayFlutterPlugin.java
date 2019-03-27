package in.appyflow.razorpayflutter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** RazorpayFlutterPlugin */
public class RazorpayFlutterPlugin implements MethodCallHandler {
  /** Plugin registration. */

  static MethodChannel.Result resultBack;

    private final Activity activity;


    private RazorpayFlutterPlugin(Activity activity) {
        this.activity = activity;


    }


    public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "razorpay_flutter");
    channel.setMethodCallHandler(new RazorpayFlutterPlugin(registrar.activity()));

    registrar.addActivityResultListener(new PluginRegistry.ActivityResultListener() {
      @Override
      public boolean onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == 101 && resultCode == 101 && data!=null) {

          HashMap<String, Object> hashMap = new HashMap<>();

          hashMap.put("status", data.getIntExtra("status", 2));
          hashMap.put("paymentId",data.getStringExtra("paymentId"));

          resultBack.success(hashMap);

        }else if(requestCode==101){

          HashMap<String, Object> hashMap = new HashMap<>();

          hashMap.put("status", 1);
          hashMap.put("paymentId","");

          resultBack.success(hashMap);

        }


        return false;
      }
    });

  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    resultBack = result;

    if (call.method.equals("showPaymentView")) {

      Log.i("Hello", call.arguments.toString());

      String txnId = call.argument("txnId").toString();
      String phone = call.argument("userPhone").toString().trim();
      String descripton = call.argument("description").toString().trim();
      String firstName = call.argument("userName").toString();
      String email = call.argument("userEmail").toString().trim();
      String keyId = call.argument("keyId").toString().trim();
      String name = call.argument("name").toString().trim();
      String image = call.argument("image").toString().trim();




//            Checkout.preload(activity);

//            Checkout.handleActivityResult(activity, 1, 2, null, new PaymentResultWithDataListener() {
//                @Override
//                public void onPaymentSuccess(String s, PaymentData paymentData) {
//
//                    Toast.makeText(activity, "Payment Success " + s, Toast.LENGTH_LONG).show();
//
//                    Log.i("TAG", s);
//
//
//                    HashMap<String, Object> hashMap = new HashMap<>();
//
//                    hashMap.put("status", 3);
//                    hashMap.put("paymentId",s);
//                    hashMap.put("error",false);
//
//
//                    resultBack.success(hashMap);
//
//
//                }
//
//                @Override
//                public void onPaymentError(int i, String s, PaymentData paymentData) {
//
//
//                    Toast.makeText(activity, "Payment Failure " + s, Toast.LENGTH_LONG).show();
//
//                    Log.i("TAG", s + " " + i);
//
//
//                    HashMap<String, Object> hashMap = new HashMap<>();
//
//                    hashMap.put("status", 1);
//                    hashMap.put("paymentId","");
//                    hashMap.put("error",true);
//                    hashMap.put("errorDescription",s);
//
//                    resultBack.success(hashMap);
//
//                }
//            },null);


      activity.startActivityForResult(new Intent(activity, RazorPayTestActivirty.class).putExtra("txnId", txnId)
                      .putExtra("userPhone", phone)
                      .putExtra("name", name)
                      .putExtra("image", image)
                      .putExtra("description", descripton)
                      .putExtra("email", email)
                      .putExtra("firstName", firstName)
                      .putExtra("keyId", keyId)
                      .putExtra("amount", Double.parseDouble(call.argument("amount").toString().trim()))

              , 101
      );


//            initializePayment(new Intent().putExtra("txnId", txnId)
//                    .putExtra("userPhone", phone)
//                    .putExtra("name", name)
//                    .putExtra("image", image)
//                    .putExtra("description", descripton)
//                    .putExtra("email", email)
//                    .putExtra("firstName", firstName)
//                    .putExtra("keyId", keyId)
//                    .putExtra("amount", Double.parseDouble(call.argument("amount").toString().trim()))
//            );


    } else {
      result.notImplemented();
    }
  }
}
