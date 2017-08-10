package evacuees.com.mbank;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import evacuees.com.mbank.DataSet.constants;

import static android.R.id.list;
import static java.security.AccessController.getContext;
import static java.util.Locale.US;

public class Profile_Activity extends AppCompatActivity {
EditText name,phoneno,email,dob;
   RadioGroup gender;
    RadioButton male,female,selectedradiobutton;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=(EditText)findViewById(R.id.name);
        phoneno=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        dob=(EditText)findViewById(R.id.dob);
        gender=(RadioGroup)findViewById(R.id.gender);
       male=(RadioButton)findViewById(R.id.male);
       female=(RadioButton)findViewById(R.id.female);
        selectedgender();
        selectdob();
        showprofile();
        updateprofile();

    }

    private void updateprofile() {

    }

    private void showprofile() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email",email);

        AsyncResponse response = new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.isEmpty() || s.equals("")) {
                    Toast.makeText(Profile_Activity.this, "check_internet_connection", Toast.LENGTH_SHORT).show();
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
        task.execute(constants.Api_Location +"");


    }

    private void selectdob() {
        final DatePickerDialog.OnDateSetListener sdate = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(Profile_Activity.this, sdate, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });
    }

    private void selectedgender() {
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender.getCheckedRadioButtonId()==-1){
                    Toast.makeText(Profile_Activity.this, "please select gender", Toast.LENGTH_SHORT).show();
                }
                else{
                    int selectedid= gender.getCheckedRadioButtonId();
                    selectedradiobutton=(RadioButton)findViewById(selectedid);
                    String gendr=selectedradiobutton.getText().toString();
                }
            }
        });
    }


}
