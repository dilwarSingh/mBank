package evacuees.com.mbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import evacuees.com.mbank.customFonts.MyButton;


public class Home_Activity extends AppCompatActivity {
    ListView listView;

    MyButton addMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}