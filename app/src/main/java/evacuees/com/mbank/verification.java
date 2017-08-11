package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class verification extends AppCompatActivity {


    TextView no, resenOtp;
    EditText enterOTP;
    Button submit;
    String otp;
    String by, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        no = (TextView) findViewById(R.id.no);
        resenOtp = (TextView) findViewById(R.id.resendOtp);
        enterOTP = (EditText) findViewById(R.id.otp);
        submit = (Button) findViewById(R.id.submit);


        otp = genrateOTP();

        by = getIntent().getExtras().getString("no");
        SendOtpByTo(by);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Otp = enterOTP.getText().toString();
                if (Otp.equals(otp)) {

                    Intent i = new Intent(verification.this, Login_Activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();


                } else {
                    Toast.makeText(verification.this, "wrong Otp Entered", Toast.LENGTH_SHORT).show();
                    enterOTP.setError("Wrong Otp Entered");
                }
            }
        });

        resenOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp = genrateOTP();
                SendOtpByTo(by);

            }
        });


    }

    private void SendOtpByTo(String by) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(by, by, otp + " verification OTP for Your mBank Account", null, null);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String genrateOTP() {


        Random r = new Random();

        int otp = Math.abs(r.nextInt()) % 1000000;

        return otp + "";


    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You Can't Go Back From This Screen", Toast.LENGTH_SHORT).show();
    }
}
