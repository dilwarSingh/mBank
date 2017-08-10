package evacuees.com.mbank;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import evacuees.com.mbank.DataSet.constants;

import static java.util.Locale.US;

public class Profile_Activity extends AppCompatActivity {
    EditText name, phoneno, email;
    TextView dob;
    RadioGroup gender;
    RadioButton male, female, selectedradiobutton;
    Calendar calendar = Calendar.getInstance();
    String gendr = "None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.name);
        phoneno = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        dob = (TextView) findViewById(R.id.dob);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

        selectedgender();
        selectdob();
        showprofile();


    }

    private void updateprofile() {
        String n = name.getText().toString();
        String m = phoneno.getText().toString();
        String eml = email.getText().toString();


        if (n.isEmpty()) {
            name.setError("Name can't be empty");
        } else if (m.isEmpty()) {
            phoneno.setError("Mobile number can't be empty");
        } else if (eml.isEmpty()) {
            email.setError("Email can't be empty");
        } else {

            HashMap<String, String> data = new HashMap<String, String>();
            data.put("name", n);
            data.put("phone", m);
            data.put("email", eml);
            data.put("dob", dob.getText().toString());
            data.put("gender", gendr);


            AsyncResponse asyncResponse = new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    if (s.isEmpty() || s.equals("") || s.equals(null)) {
                        Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                        if (s.equals("Successfully updated")) {


                        }

                    }
                }
            };

            PostResponseAsyncTask task = new PostResponseAsyncTask(Profile_Activity.this, data, "Updating Please Wait......", asyncResponse);

            task.execute(constants.Api_Location + "updateProfile.php");


        }

    }

    private void showprofile() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", constants.EMAIL);

        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.isEmpty() || s.equals("")) {
                    Toast.makeText(Profile_Activity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            name.setText(object.getString("name"));
                            phoneno.setText(object.getString("phone"));
                            email.setText(object.getString("email"));


                        }


                    } catch (Exception e) {
                        Toast.makeText(Profile_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        PostResponseAsyncTask task = new PostResponseAsyncTask(Profile_Activity.this, map, "Loading Paper...", response);
        task.execute(constants.Api_Location + "getAccountInfo.php");


    }

    private void selectdob() {
        final DatePickerDialog.OnDateSetListener DOB = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat format = new SimpleDateFormat(myFormat, US);
                dob.setText(format.format(calendar.getTime()));
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Profile_Activity.this, DOB, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });
    }


    private void selectedgender() {
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Profile_Activity.this, "please select gender", Toast.LENGTH_SHORT).show();
                } else {
                    int selectedid = gender.getCheckedRadioButtonId();
                    selectedradiobutton = (RadioButton) findViewById(selectedid);
                    gendr = selectedradiobutton.getText().toString();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                updateprofile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
