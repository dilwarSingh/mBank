package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.Calendar;
import java.util.HashMap;

import static evacuees.com.mbank.DataSet.constants.ACCOUNT;
import static evacuees.com.mbank.DataSet.constants.Api_Location;
import static evacuees.com.mbank.DataSet.constants.BALANCE;

public class sendMoney_Activity extends AppCompatActivity {


    EditText ReciverNo, money;
    Button sendMoney;

    String accNo, bal;
    TextInputLayout amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_);


        ReciverNo = (EditText) findViewById(R.id.accNo);
        money = (EditText) findViewById(R.id.money);
        sendMoney = (Button) findViewById(R.id.sendMoney);
        amount = (TextInputLayout) findViewById(R.id.amount);


        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float Money = Long.parseLong(money.getText().toString());

                if (Money < Float.parseFloat(BALANCE)) {
                    amount.setError("Insufficient Balance");
                } else {

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("Saccount", ACCOUNT);
                    map.put("Raccount", ReciverNo.getText().toString());
                    map.put("mins", (Float.parseFloat(BALANCE) - Money) + "");
                    map.put("amount", Money + "");
                    map.put("date", getCurrentDate());
                    map.put("time", getCurrentTime());


                    AsyncResponse response = new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {

                            if (s.isEmpty() || s.equals("") || s.equals(null)) {
                                Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                if (s.equals("Successfully Send")) {

                                    Intent intent = new Intent(sendMoney_Activity.this, Home_Activity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    };

                    PostResponseAsyncTask task = new PostResponseAsyncTask(sendMoney_Activity.this, map, "Sending Money...", response);
                    task.execute(Api_Location + "sendMoney.php");

                }
            }
        });
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

