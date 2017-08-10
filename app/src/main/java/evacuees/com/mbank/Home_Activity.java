package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import evacuees.com.mbank.Adapters.TransactionsCustomListAdaptor;
import evacuees.com.mbank.DataSet.constants;
import evacuees.com.mbank.popups.addMoneyPopup;

import static evacuees.com.mbank.DataSet.constants.Api_Location;


public class Home_Activity extends AppCompatActivity {


    ImageButton Profile, MapLoc;
    TextView AccountNo, AccountBal;
    Button addMoney, sendMoney;
    ListView tracHis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bindViews();
        tanschist();
        init();

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyClicked();
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Profile_Activity.class);
                startActivity(intent);

            }
        });

    }

    private void tanschist() {
        final ArrayList listt = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        map.put("account_no", no);

        final List<String> sendtoo = new ArrayList<>();
        final List<String> date = new ArrayList<>();
        final List<String> time= new ArrayList<>();
        final List<String> amount = new ArrayList<>();



        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String jsonData) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        sendtoo.add(object.getString("byto"));
                        date.add(object.getString("date"));
                        time.add(object.getString("time"));
                        amount.add(object.getString("money"));

                    }
                    for (int i = 0; i < sendtoo.size(); i++) {
                        TransactionsListData ld = new TransactionsListData(sendtoo.get(i), date.get(i), time.get(i), amount.get(i), img.get(i));
                        listt.add(ld);
                    }
                    tracHis.setAdapter(new TransactionsCustomListAdaptor(Home_Activity.this, listt));


                } catch (Exception e) {
                    Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();


                }


            }
        };


        PostResponseAsyncTask task = new PostResponseAsyncTask(Home_Activity.this, map, true, response);
        task.execute(constants.Api_Location + "");

    }

    private void addMoneyClicked() {

        addMoneyPopup popup = new addMoneyPopup(Home_Activity.this);
        popup.show();

    }

    private void init() {

        HashMap<String, String> map = new HashMap<>();

        map.put("email", constants.EMAIL);

        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String jsonData) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    String no = "00", bal = "00.00";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        bal = "Rs. " + (object.getString("account_balance"));
                        no =  "A/C: " +(object.getString("account_no"));
                    }

                    AccountNo.setText(no);
                    AccountBal.setText(bal);


                } catch (Exception e) {
                    Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        PostResponseAsyncTask task = new PostResponseAsyncTask(Home_Activity.this, map, true, response);
        task.execute(Api_Location + "getAccountInfo.php");


    }

    private void bindViews() {
        Profile = (ImageButton) findViewById(R.id.profile);
        MapLoc = (ImageButton) findViewById(R.id.map_loc);
        AccountNo = (TextView) findViewById(R.id.account_number);
        AccountBal = (TextView) findViewById(R.id.account_balance);
        addMoney = (Button) findViewById(R.id.addMoney);
        sendMoney = (Button) findViewById(R.id.sendMoney);
        tracHis = (ListView) findViewById(R.id.tracHis);

    }


}