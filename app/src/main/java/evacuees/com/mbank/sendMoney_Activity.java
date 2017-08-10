package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import evacuees.com.mbank.DataSet.constants;

import static evacuees.com.mbank.DataSet.constants.ACCOUNT;
import static evacuees.com.mbank.DataSet.constants.Api_Location;
import static evacuees.com.mbank.DataSet.constants.BALANCE;

public class sendMoney_Activity extends AppCompatActivity {


    EditText ReciverNo, money;
    Button sendMoney;
    String reciversAccountBalance;
    TextInputLayout amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_);


        ReciverNo = (EditText) findViewById(R.id.accNo);
        money = (EditText) findViewById(R.id.money);
        sendMoney = (Button) findViewById(R.id.sendMoney);
        amount = (TextInputLayout) findViewById(R.id.amount);
        TextView tv = (TextView) findViewById(R.id.bal);
        tv.setText(constants.BALANCE);

        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final float Money = Float.parseFloat(money.getText().toString());

                if (Money > Float.parseFloat(BALANCE)) {
                    amount.setError("Insufficient Balance");
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("account_no", ReciverNo.getText().toString().trim());


                    AsyncResponse response = new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (s.isEmpty() || s.equals("")) {
                                Toast.makeText(sendMoney_Activity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        reciversAccountBalance = (object.getString("account_balance"));

                                    }

                                    System.out.println("/////////////////////////////" + Money + "9999999999999999999999999999999");

                                    sendMoney1(Money);


                                } catch (Exception e) {
                                    Toast.makeText(sendMoney_Activity.this, "Invalid Account No.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    };

                    PostResponseAsyncTask task = new PostResponseAsyncTask(sendMoney_Activity.this, map, "Checking Profile...", response);
                    task.execute(constants.Api_Location + "getAccountInfoAccount.php");


                }
            }
        });
    }

    public void sendMoney1(Float Money) {
        HashMap<String, String> map = new HashMap<String, String>();

        Log.d("money", Money + "iacb");
        System.out.println("/////////////////////////////" + Money + "9999999999999999999999999999999");

        map.put("Saccount", ACCOUNT);
        map.put("Raccount", ReciverNo.getText().toString());
        map.put("USaccount", "_" + ACCOUNT);
        map.put("URaccount", "_" + ReciverNo.getText().toString());
        map.put("mins", (Float.parseFloat(BALANCE) - Money) + "");
        map.put("maxs", (Float.parseFloat(reciversAccountBalance) - Money) + "");
        map.put("Money", Money + "");
        map.put("date", getCurrentDate());
        map.put("time", getCurrentTime());
        System.out.println("/////////////////////////////" + Money + "9999999999999999999999999999999");


        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.isEmpty() || s.equals("") || s.equals(null)) {
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    if (s.equals("Successfully Send")) {

                        Intent i = new Intent(sendMoney_Activity.this, Home_Activity.class);

                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                }
            }
        };

        PostResponseAsyncTask task = new PostResponseAsyncTask(sendMoney_Activity.this, map, "Sending Money...", response);
        task.execute(Api_Location + "sendMoney.php");


    }

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();

        int mm = calendar.get(Calendar.MINUTE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        String m = mm + "";
        String h = hh + "";
        if ((mm < 10)) {
            m = "0" + mm;
        }
        if ((hh < 10)) {
            h = "0" + hh;
        }
        String t = h + ":" + m;
        return t;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String m = (calendar.get(Calendar.MONTH) + 1) + "";
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "";
        if (m.length() == 1) {
            m = "0" + m;
        }
        if (date.length() == 1) {
            date = "0" + date;
        }

        return (date + "/" + m + "/" + calendar.get(Calendar.YEAR));
    }
}

