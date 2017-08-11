package evacuees.com.mbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

public class Transaction_Activity extends AppCompatActivity {
ListView transactionhis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        transactionhis=(ListView)findViewById(R.id.listv);
        final ArrayList<TransactionsListData> listt = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        String tab = "_" + constants.ACCOUNT;
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
                    transactionhis.setAdapter(new TransactionsCustomListAdaptor(Transaction_Activity.this, listt));


                } catch (Exception e) {
                    Toast.makeText(Transaction_Activity.this,   e.getMessage(), Toast.LENGTH_LONG).show();


                }


            }
        };


        PostResponseAsyncTask task = new PostResponseAsyncTask(Transaction_Activity.this, map, true, response);
        task.execute(constants.Api_Location + "getTransactionHistory.php");
    }
}
