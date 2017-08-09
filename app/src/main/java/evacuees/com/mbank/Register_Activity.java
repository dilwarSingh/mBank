package evacuees.com.mbank;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

import evacuees.com.mbank.DataSet.constants;

public class Register_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText fullname, email, mobile, password, repasword;
        Button register;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullname = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        repasword = (EditText) findViewById(R.id.rePassword);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = fullname.getText().toString();
                String m = mobile.getText().toString();
                String eml = email.getText().toString();
                String pwd = password.getText().toString();
                String rpwd = repasword.getText().toString();
                if(fn.isEmpty()){
                fullname.setError("Name can't be empty");
                }
                else if(m.isEmpty()){
                    mobile.setError("Mobile number can't be empty");
                }
                else if(eml.isEmpty()){
                    email.setError("Email can't be empty");
                }
                if ((!pwd.isEmpty())||pwd.equals(rpwd)) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("name", fn);
                    data.put("phone", m);
                    data.put("email", eml);
                    data.put("password", pwd);


                    AsyncResponse asyncResponse = new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {

                            if (s.isEmpty() || s.equals("") || s.equals(null)) {
                                Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                if (s.equals("Successfully registered")) {

                                    fullname.setText("");
                                    mobile.setText("");
                                    password.setText("");
                                    repasword.setText("");
                                    email.setText("");

                                }

                            }
                        }
                    };

                    PostResponseAsyncTask task = new PostResponseAsyncTask(Register_Activity.this, data, "Registering Please Wait......", asyncResponse);

                    task.execute(constants.Api_Location + "register.php");
                } else {
                    password.setError("Password didn't match");
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_in_reverse, R.anim.slide_out_reverse);
                finish();
            }
        }, 300);

    }
}}
