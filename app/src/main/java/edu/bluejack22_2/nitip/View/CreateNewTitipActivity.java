package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class CreateNewTitipActivity extends AppCompatActivity {

    private EditText etTitipName;
    private EditText etTitipCloseTime;
    private Button btnCreateTitip;
    private Button btnBack;
    private TitipViewModel titipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_titip);

        initialize();
        setListener();
    }

    private void initialize() {
        etTitipName = findViewById(R.id.etTitipName);
        etTitipCloseTime = findViewById(R.id.etTitipCloseTime);
        btnCreateTitip = findViewById(R.id.btnCreateTitip);
        btnBack = findViewById(R.id.btnBack);
        titipViewModel = new TitipViewModel();

        etTitipCloseTime.setFocusable(false);
        etTitipCloseTime.setFocusableInTouchMode(false);
    }

    private void setListener() {
        etTitipCloseTime.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
                new TimePickerDialog(this, (view1, hourOfDay, minute1) -> {
                    Calendar chosenDate = Calendar.getInstance();
                    chosenDate.set(year1, monthOfYear, dayOfMonth, hourOfDay, minute1);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    etTitipCloseTime.setText(format.format(chosenDate.getTime()));
                }, hour, minute, true).show();
            }, year, month, day).show();
        });

        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnCreateTitip.setOnClickListener(e -> {
            String titipName = etTitipName.getText().toString();
            String closeTime = etTitipCloseTime.getText().toString();

            Titip titip = new Titip(titipName, closeTime, new ArrayList<TitipDetail>());
            Response response = titipViewModel.CreateTitip(titip);
            
            if (response.getError() != null) {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityChanger.changeActivity(this, HomeActivity.class);
                Toast.makeText(this, "New titip created", Toast.LENGTH_SHORT).show();
            }
        });
    }
}