package evacuees.com.mbank;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.Locale.US;

public class Profile_Activity extends AppCompatActivity {
EditText name,phoneno,email,dob;
    Button save;
    RadioButton gender;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=(EditText)findViewById(R.id.);
        phoneno=(EditText)findViewById(R.id.);
        email=(EditText)findViewById(R.id.);
        dob=(EditText)findViewById(R.id.);
        save=(Button)findViewById(R.id.);
        gender=(RadioButton)findViewById(R.id.);

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

}
