package evacuees.com.mbank.popups;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import evacuees.com.mbank.R;

/**
 * Created by Dilwar Singh on 8/6/2017.
 */

public class addMoneyPopup extends Dialog {


    Activity activity;
    Dialog dialog;
    Button yes, no;
    float value = 0.0f;
    EditText money;


    public addMoneyPopup(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.popup_add_money);

        yes = (Button) findViewById(R.id.addUp);
        no = (Button) findViewById(R.id.cancelUp);
        money = (EditText) findViewById(R.id.money);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show();
                String s = String.format("%.2f", Float.parseFloat(money.getText().toString()));
                value = Float.parseFloat(s);
                dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "No", Toast.LENGTH_SHORT).show();
                hide();
            }
        });
    }
}
