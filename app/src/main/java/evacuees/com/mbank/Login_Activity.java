package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

import evacuees.com.mbank.DataSet.constants;

public class Login_Activity extends AppCompatActivity {
    TextView register, login;
    EditText Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                        startActivity(intent);
                        finish();

                    }
                }, 1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checker()) {
                    LoginHit();
                }
            }
        });

    }

    private boolean checker() {
        if (TextUtils.isEmpty(Email.getText()) || Email.getText().length() < 7) {
            Email.setError("Invalid Email");
            return false;
        } else if (TextUtils.isEmpty(Password.getText()) || Password.getText().length() < 5) {
            Password.setError("Invalid Password");
            return false;
        }
        return true;

    }

    private void LoginHit() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String s) {

            }
        };
        PostResponseAsyncTask task = new PostResponseAsyncTask();
        task.execute(constants.Api_Location+"login.php");

    }
}
