package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import evacuees.com.mbank.DataSet.constants;

public class Login_Activity extends AppCompatActivity {
    TextView register, login;
    EditText Email, Password;
    TextInputLayout passwordTil, emailTil;
    String emailPattern = "^[a-zA-Z][\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    Pattern patternE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        passwordTil = (TextInputLayout) findViewById(R.id.passwordTil);
        emailTil = (TextInputLayout) findViewById(R.id.emailTil);
        patternE = Pattern.compile(emailPattern);
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
        Matcher matcherE = patternE.matcher(Email.getText().toString());
        if ((!matcherE.find())) {
            emailTil.setError("Invalid Email");
            return false;
        } else if (Password.getText().length() < 6) {
            passwordTil.setError("Password is wrong");
            return false;
        }
        return true;

    }

    private void LoginHit() {
        final String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.isEmpty() || s.equals("") || s.equals(null)) {
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    if (s.equals("Success")) {
                        constants.EMAIL = email;
                        Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        PostResponseAsyncTask task = new PostResponseAsyncTask(Login_Activity.this, map, "Logging you in...", response);
        task.execute(constants.Api_Location + "login.php");

    }
}
