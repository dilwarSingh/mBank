package evacuees.com.mbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import evacuees.com.mbank.customFonts.MyButton;
import evacuees.com.mbank.popups.addMoneyPopup;


public class Home_Activity extends AppCompatActivity {
    ListView listView;

    MyButton addMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(android.R.id.list);

       //  addMoney = (MyButton) findViewById(R.id.addMoney);
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            arrayList.add("Item: " + i);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(adapter);

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMoneyClicked();
            }
        });

    }

    private void AddMoneyClicked() {

        addMoneyPopup addMoneyPopup = new addMoneyPopup(this);
        addMoneyPopup.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        new MenuInflater(this).inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
