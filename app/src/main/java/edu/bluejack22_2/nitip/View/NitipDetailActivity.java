package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.bluejack22_2.nitip.Adapter.NitipDetailAdapter;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
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
    private Button btnShareBill;
    private Titip currentTitip;
    public static NitipDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitip_detail);
        initializeVariable();
        setValues();
    }


    private void initializeVariable() {
        titipViewModel = new TitipViewModel();

        btnBack = findViewById(R.id.btnBack);
        tvTitipTitle = findViewById(R.id.tvTitipTitle);
        tvEntrusteeEmail = findViewById(R.id.tvEntrusteeEmail);
        tvClosesAt = findViewById(R.id.tvClosesAt);
        rvTitipDetails = findViewById(R.id.rvTitipDetails);
        btnTitip = findViewById(R.id.btnNitip);
        btnShareBill = findViewById(R.id.btnBillDebtors);
    }

    private void setValues() {
        String titipID = getIntent().getExtras().getString("TitipID");

        titipViewModel.getTitipById(titipID).observe(this, titip -> {
            tvTitipTitle.setText(titip.getTitip_name());
            tvEntrusteeEmail.setText(titip.getEntruster_email());
            tvClosesAt.setText(getResources().getString(R.string.closes_at) + titip.getClose_time().substring(titip.getClose_time().length() - 5));
            currentTitip = new Titip(titip.getTitip_name(), titip.getClose_time(),
                titip.getGroup_code(), titip.getGroup_name(), titip.getTitip_detail());
            currentTitip.setId(titipID);
            currentTitip.setEntruster_email(titip.getEntruster_email());

            setRecyclerView();
            setListener();
            setInteraction();
        });

        btnShareBill.setVisibility(View.GONE);
        btnTitip.setVisibility(View.VISIBLE);
    }

    private void setInteraction() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        FirebaseAuth auth = FirebaseAuth.getInstance();

        try {
            Date closeTime = df.parse(currentTitip.getClose_time());
            if (!closeTime.after(currentTime) &&
                auth.getCurrentUser().getEmail().equals(currentTitip.getEntruster_email())) {

                btnTitip.setVisibility(View.GONE);
                btnShareBill.setVisibility(View.VISIBLE);

            }
        } catch (ParseException p) {
            throw new RuntimeException(p);
        }

    }

    private void setRecyclerView() {
        adapter = new NitipDetailAdapter(this, currentTitip.getTitip_detail(), currentTitip.getId(), currentTitip.getClose_time());
        rvTitipDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rvTitipDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnTitip.setOnClickListener(e -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            boolean hasTitipDetail = false;

            for (TitipDetail titipDetail : NitipDetailAdapter.data) {
                if (titipDetail.getUser().getEmail().equals(firebaseAuth.getCurrentUser().getEmail())) {
                    hasTitipDetail = true;
                }
            }

            Date currentTime = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                Date closeTime = df.parse(currentTitip.getClose_time());
                if (closeTime.after(currentTime)) {
                    if (hasTitipDetail) {
                        Toast.makeText(this, getResources().getString(R.string.you_already_titip), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent next = new Intent(this, CreateTitipDetailActivity.class);
                    next.putExtra("GroupName", this.currentTitip.getGroup_name());
                    next.putExtra("TitipID", getIntent().getExtras().getString("TitipID"));
                    startActivity(next);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.nitip_closed), Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException p) {
                throw new RuntimeException(p);
            }
        });

        btnShareBill.setOnClickListener(e -> {
            Intent intent = new Intent(this, BillDebtorsActivity.class);
            intent.putExtra("TitipID", getIntent().getStringExtra("TitipID"));
            startActivity(intent);
        });
    }
}