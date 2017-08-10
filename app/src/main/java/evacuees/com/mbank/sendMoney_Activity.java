package evacuees.com.mbank;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

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
                    map.put("amount", money.getText().toString());


                    AsyncResponse response = new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {

                            if (s.isEmpty() || s.equals("") || s.equals(null)) {
                                Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    PostResponseAsyncTask task = new PostResponseAsyncTask(sendMoney_Activity.this, map, "Sending Money...", response);
                    task.execute(Api_Location + "sendMoney.php");

                }
            }
        });
    }
}

