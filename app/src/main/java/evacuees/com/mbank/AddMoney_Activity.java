package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.Calendar;
import java.util.HashMap;

import evacuees.com.mbank.DataSet.constants;

public class AddMoney_Activity extends AppCompatActivity {
    EditText money;
    Button add_money;
    String rupees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        money = (EditText) findViewById(R.id.money);
        add_money = (Button) findViewById(R.id.addmoney);

        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rupees = money.getText().toString();

                String addrupees = "" + (Float.parseFloat(constants.BALANCE) + Float.parseFloat(rupees));

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("account", constants.ACCOUNT);
                data.put("underAccount", "_" + constants.ACCOUNT);
                data.put("account_balance", addrupees);
                data.put("money", rupees);
                data.put("date", getCurrentDate());
                data.put("time", getCurrentTime());


                AsyncResponse asyncResponse = new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {

                        if (s.isEmpty() || s.equals("") || s.equals(null)) {
                            Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            if (s.equals("Successfully Added")) {

                                Intent intent = new Intent(AddMoney_Activity.this, Home_Activity.class);
                                startActivity(intent);
                            }

                        }
                    }
                };

                PostResponseAsyncTask task = new PostResponseAsyncTask(AddMoney_Activity.this, data, "Adding Please Wait......", asyncResponse);

                task.execute(constants.Api_Location + "addMoney.php");


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
