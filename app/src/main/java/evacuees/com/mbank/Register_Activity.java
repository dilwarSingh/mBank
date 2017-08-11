package evacuees.com.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import evacuees.com.mbank.DataSet.constants;

public class Register_Activity extends AppCompatActivity {

    String emailPattern = "^[a-zA-Z][\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    String phonePattern = "^[7-9][0-9]{9}$";

    Pattern patternE;
    Pattern patternPH;

    TextView logintoMbank;

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
        logintoMbank = (TextView) findViewById(R.id.loginMbank);
        register = (Button) findViewById(R.id.register);
        patternE = Pattern.compile(emailPattern);
        patternPH = Pattern.compile(phonePattern);

        logintoMbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = fullname.getText().toString();
                String m = mobile.getText().toString();
                String eml = email.getText().toString();
                String pwd = password.getText().toString();
                String rpwd = repasword.getText().toString();

                Random random = new Random();
                long min = 100000000L;
                long max = 1000000000000L;
                final long account_no = min + ((long) (random.nextDouble() * (max - min)));

                Matcher matcherE = patternE.matcher(eml);
                Matcher matcherPH = patternPH.matcher(m);
                if (fn.isEmpty()) {
                    fullname.setError("Name can't be empty");
                } else if ((!matcherPH.find())) {
                    mobile.setError("Mobile number is not valid");
                } else if ((!matcherE.find())) {
                    email.setError("Email is not valid");
                } else if (pwd.length() < 6) {
                    password.setError("Password can't be less than 6 characters");
                } else if (!pwd.equals(rpwd)) {
                    repasword.setError("Password didn't match");
                } else {

                    HashMap<String, String> data = new HashMap<String, String>();

                    data.put("name", fn);
                    data.put("phone", m);
                    data.put("email", eml);
                    data.put("password", pwd);
                    data.put("time", getCurrentTime());
                    data.put("date", getCurrentDate());
                    data.put("account_no", account_no + "");
                    data.put("account_balance", "00.00");
                    data.put("dob", "DD/MM/YYYY");
                    data.put("gender", "none");


                    AsyncResponse asyncResponse = new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {

                            if (s.isEmpty() || s.equals("") || s.equals(null)) {
                                Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                if (s.equals("successfully registered")) {


                               /*     fullname.setText("");
                                    mobile.setText("");
                                    password.setText("");
                                    repasword.setText("");
                                    email.setText("");*/

                                    Intent intent = new Intent(Register_Activity.this, verification.class);
                                    intent.putExtra("no", mobile.getText().toString());
                                    startActivity(intent);
                                    finish();


                                }

                            }
                        }
                    };

                    PostResponseAsyncTask task = new PostResponseAsyncTask(Register_Activity.this, data, "Registering Please Wait......", asyncResponse);

                    task.execute(constants.Api_Location + "register.php");


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

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();

        int mm = calendar.get(Calendar.MINUTE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        String m = mm + "";
        String h = hh + "";
        if ((mm < 10)) {
            m = "0" + mm;
        }
        if ((hh < 10)) {
            h = "0" + hh;
        }
        String t = h + ":" + m;
        return t;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String m = (calendar.get(Calendar.MONTH) + 1) + "";
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "";
        if (m.length() == 1) {
            m = "0" + m;
        }
        if (date.length() == 1) {
            date = "0" + date;
        }

        return (date + "/" + m + "/" + calendar.get(Calendar.YEAR));
    }

}
