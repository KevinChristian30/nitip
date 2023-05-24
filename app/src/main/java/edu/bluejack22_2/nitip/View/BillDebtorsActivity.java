package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.bluejack22_2.nitip.Adapter.BillDebtorsAdapter;
import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.BillViewModel;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class BillDebtorsActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnBill;
    private RecyclerView rvTitip;
    private TitipViewModel titipViewModel;

    private BillViewModel billViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_debtors);

        initializeVariables();
        setValues();
    }

    private void initializeVariables() {
        btnBack = findViewById(R.id.btnBack);
        btnBill = findViewById(R.id.btnBill);
        rvTitip = findViewById(R.id.rvTitip);

        titipViewModel = new TitipViewModel();
        billViewModel = new BillViewModel();
    }

    private void setValues() {
        String titipID = getIntent().getExtras().getString("TitipID");

        titipViewModel.getTitipById(titipID).observe(this, titip -> {
            setRecyclerView(titip.getTitip_detail());
            setListener();
        });
    }

    private void setRecyclerView(ArrayList<TitipDetail> titipDetails) {
        BillDebtorsAdapter adapter = new BillDebtorsAdapter(this, titipDetails);
        rvTitip.setAdapter(adapter);
        rvTitip.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnBill.setOnClickListener(e -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            for (int i = 0; i < rvTitip.getChildCount(); i++) {
                View view = rvTitip.getChildAt(i);
                TextView tvDebtorEmail = view.findViewById(R.id.tvDebtorEmail);
                EditText etAmount = view.findViewById(R.id.etAmount);

                Bill bill = new Bill(tvDebtorEmail.getText().toString(),
                    firebaseAuth.getCurrentUser().getEmail(),
                    Integer.parseInt(etAmount.getText().toString()),
                    "Pending Payment",
                    Calendar.getInstance().getTime().toString()
                    );

                billViewModel.createBill(bill);
            }

            finish();
        });
    }

}