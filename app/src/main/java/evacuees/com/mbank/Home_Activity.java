package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import static evacuees.com.mbank.DataSet.constants.Api_Location;


public class Home_Activity extends AppCompatActivity {


    ImageButton Profile, MapLoc;
    TextView AccountNo, AccountBal;
    Button addMoney, sendMoney;
    ListView tracHis;
    String no = "00", bal = "00.00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bindViews();
        init();


        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyClicked();
            }
        });

        MapLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Profile_Activity.class);
                startActivity(intent);

            }
        });
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, AddMoney_Activity.class);
                startActivity(intent);

            }
        });
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, sendMoney_Activity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Logout:
                Intent i = new Intent(Home_Activity.this, Login_Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                break;
            case R.id.transactionhis:
                Intent i1 = new Intent(Home_Activity.this, Transaction_Activity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);

                break;
            case R.id.profile:
                Intent i2 = new Intent(Home_Activity.this, Profile_Activity.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i2);

                break;
            case R.id.map:
                Intent i3 = new Intent(Home_Activity.this, MapsActivity.class);
                i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i3);

                break;
            case R.id.sendMoney:
                Intent i4 = new Intent(Home_Activity.this, sendMoney_Activity.class);
                i4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i4);

                break;
            case R.id.addMoney:
                Intent i5 = new Intent(Home_Activity.this, AddMoney_Activity.class);
                i5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i5);

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void tanschist(String fgh) {
        final ArrayList<TransactionsListData> listt = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        String tab = "_" + fgh;
        Log.d("tab", tab);
        map.put("Uaccount", tab);

        final List<String> sendtoo = new ArrayList<>();
        final List<String> date = new ArrayList<>();
        final List<String> time = new ArrayList<>();
        final List<String> amount = new ArrayList<>();
        final List<String> status = new ArrayList<>();


        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String jsonData) {

                try {
                    Log.d("sdfgh", jsonData);

                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        sendtoo.add(object.getString("byto"));
                        date.add(object.getString("date"));
                        time.add(object.getString("time"));
                        amount.add(object.getString("money"));
                        status.add(object.getString("status"));

                    }
                    for (int i = 0; i < sendtoo.size(); i++) {
                        TransactionsListData ld = new TransactionsListData(sendtoo.get(i), date.get(i), time.get(i), amount.get(i), status.get(i));
                        listt.add(ld);
                    }
                    tracHis.setAdapter(new TransactionsCustomListAdaptor(Home_Activity.this, listt));


                } catch (Exception e) {
                    Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();


                }


            }
        };


        PostResponseAsyncTask task = new PostResponseAsyncTask(Home_Activity.this, map, true, response);
        task.execute(constants.Api_Location + "getTransactionHistory.php");

    }

    private void addMoneyClicked() {


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


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        bal = (object.getString("account_balance"));
                        no = (object.getString("account_no"));

                        constants.BALANCE = bal;
                        constants.ACCOUNT = no;
                    }

                    AccountNo.setText("A/C: " + no);
                    AccountBal.setText("Rs. " + bal);
                    tanschist(no);


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