package evacuees.com.mbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

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
        money=(EditText)findViewById(R.id.money);
        add_money=(Button)findViewById(R.id.addmoney);
        rupees=money.getText().toString();
        addrupees();
    }

    private void addrupees() {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("account_balance", n);



        AsyncResponse asyncResponse = new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.isEmpty() || s.equals("") || s.equals(null)) {
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    if (s.equals("Successfully updated")) {


                    }

                }
            }
        };

        PostResponseAsyncTask task = new PostResponseAsyncTask(AddMoney_Activity.this, data, "Updating Please Wait......", asyncResponse);

        task.execute(constants.Api_Location + "updateProfile.php");


    }

    }
}
