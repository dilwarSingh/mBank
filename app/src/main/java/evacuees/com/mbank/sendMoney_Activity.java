package evacuees.com.mbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import static evacuees.com.mbank.DataSet.constants.ACCOUNT;

public class sendMoney_Activity extends AppCompatActivity {


    EditText ReciverNo, money;
    Button sendMoney;

    String accNo,bal,;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money_);


        ReciverNo = (EditText) findViewById(R.id.accNo);
        money = (EditText) findViewById(R.id.money);
        sendMoney = (Button) findViewById(R.id.sendMoney);


        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long Money = Long.parseLong(money.getText().toString());

                HashMap<String,String> map = new HashMap<String, String>();

                map.put("Saccount",ACCOUNT);
                map.put("Raccount",ACCOUNT);




            }
        });



    }
}
