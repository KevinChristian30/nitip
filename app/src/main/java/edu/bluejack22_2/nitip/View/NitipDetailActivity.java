package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.bluejack22_2.nitip.Adapter.NitipDetailAdapter;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class NitipDetailActivity extends AppCompatActivity {

    private TitipViewModel titipViewModel;
    private Button btnBack;
    private TextView tvTitipTitle;
    private TextView tvEntrusteeEmail;
    private TextView tvClosesAt;
    private RecyclerView rvTitipDetails;
    private Button btnTitip;
    private Titip currentTitip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitip_detail);

        initializeVariable();
        setValues();

//        Log.i("ASD", currentTitip.getTitip_name());
//        setRecyclerView();
//        setListener();
    }

    private void initializeVariable() {
        titipViewModel = new TitipViewModel();

        btnBack = findViewById(R.id.btnBack);
        tvTitipTitle = findViewById(R.id.tvTitipTitle);
        tvEntrusteeEmail = findViewById(R.id.tvEntrusteeEmail);
        tvClosesAt = findViewById(R.id.tvClosesAt);
        rvTitipDetails = findViewById(R.id.rvTitipDetails);
        btnTitip = findViewById(R.id.btnNitip);
    }

    private void setValues() {
        String titipID = getIntent().getExtras().getString("TitipID");

        titipViewModel.getTitipById(titipID).observe(this, titip -> {
            tvTitipTitle.setText(titip.getTitip_name());
            tvEntrusteeEmail.setText(titip.getEntruster_email());
            tvClosesAt.setText("Closes At " + titip.getClose_time().substring(titip.getClose_time().length() - 5));
            currentTitip = new Titip(titip.getTitip_name(), titip.getClose_time(),
                titip.getGroup_code(), titip.getGroup_name(), titip.getTitip_detail());

            setRecyclerView();
            setListener();
        });
    }

    private void setRecyclerView() {
//        ArrayList<TitipDetail> data = new ArrayList<>();
//        data.add(new TitipDetail(new User("", "kdotchrist30@gmail.com", "", ""), "Lemonade"));
        NitipDetailAdapter adapter = new NitipDetailAdapter(this, currentTitip.getTitip_detail());
        rvTitipDetails.setAdapter(adapter);

        rvTitipDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnTitip.setOnClickListener(e -> {
            Date currentTime = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                Date closeTime = df.parse(currentTitip.getClose_time());
                if (closeTime.after(currentTime)) {
                    Intent next = new Intent(this, CreateTitipDetailActivity.class);
                    next.putExtra("GroupName", this.currentTitip.getGroup_name());
                    next.putExtra("TitipID", getIntent().getExtras().getString("TitipID"));
                    startActivity(next);
                } else {
                    Toast.makeText(this, "Nitip Closed", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException p) {
                throw new RuntimeException(p);
            }

        });
    }
}