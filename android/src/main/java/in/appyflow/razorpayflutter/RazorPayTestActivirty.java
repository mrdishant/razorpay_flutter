package in.appyflow.razorpayflutter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class RazorPayTestActivirty extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay_test_activirty);

        Checkout.preload(getApplicationContext());

        initializePayment();

    }

    private void initializePayment() {

        Intent i = getIntent();

        String txnId = i.getStringExtra("txnId").toString();
        String phone = i.getStringExtra("userPhone").toString().trim();
        String description = i.getStringExtra("description").toString().trim();
        String firstName = i.getStringExtra("firstName").toString();
        String keyId = i.getStringExtra("keyId").toString();
        double amount = i.getDoubleExtra("amount", 100.0);
        String email = i.getStringExtra("email").toString().trim();
        String name = i.getStringExtra("name").toString().trim();
        String image = i.getStringExtra("image").toString().trim();


        Log.i("TAG", phone + " " + txnId + " " + description + " " + email);

        Checkout checkout = new Checkout();


//        Log.i("TAG", phone + " " + txnId + " " + productName + " " + email);



        checkout.setKeyID(keyId);

        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher);



        /**
         * Reference to current activity
         */
        final Activity activity = RazorPayTestActivirty.this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {

            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", name);

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", description);

            options.put("currency", "INR");

            options.put("image", image);

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */

            String am = (amount * 100) + "";
            Log.i("TAG", am);

            options.put("amount", am);


            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phone);

            options.put("prefill", preFill);

            checkout.open(activity, options);


//            JSONObject options = new JSONObject();
//
//            /**
//             * Merchant Name
//             * eg: Rentomojo || HasGeek etc.
//             */
//            options.put("name", "Splash WorkSpaces");
//
//            /**
//             * Description can be anything
//             * eg: Order #123123
//             *     Invoice Payment
//             *     etc.
//             */
//
//
//            options.put("order_id","Order #"+txnId);
//
//            options.put("description", productName);
//
//            options.put("currency", "INR");
//
//            options.put("image", "https://i1.wp.com/www.vectorico.com/wp-content/uploads/2018/02/Google-Plus-Icon.png?resize=300%2C300");
//
//            /**
//             * Amount is always passed in PAISE
//             * Eg: "500" = Rs 5.00
//             */
//            String  am=(amount*100)+"";
//            Log.i("TAG",am);
//            options.put("amount", am);
//
//
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", email);
//            preFill.put("contact", phone);
//            options.put("prefill", preFill);
//
//            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("LOG", "Error in starting Razorpay Checkout", e);
        }

    }


    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(getApplicationContext(), "Payment Success " + s, Toast.LENGTH_LONG).show();

        Log.i("TAG", s);

        Intent intent = new Intent();
        intent.putExtra("status", 3);
        intent.putExtra("paymentId",s);
        setResult(101, intent);
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Failure " + s, Toast.LENGTH_LONG).show();

        Log.i("TAG", s+" "+i);

        Intent intent = new Intent();
        intent.putExtra("status", 1);
        intent.putExtra("paymentId","");
        setResult(101, intent);
        finish();

    }


}
