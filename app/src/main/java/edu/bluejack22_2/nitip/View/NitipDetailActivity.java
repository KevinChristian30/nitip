package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import edu.bluejack22_2.nitip.Model.Titip;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitip_detail);

        initializeVariable();
        setValues();
        setListener();
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
        });
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });
    }

}