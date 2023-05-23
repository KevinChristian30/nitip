package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.bluejack22_2.nitip.Adapter.NitipDetailAdapter;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class CreateTitipDetailActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Button btnBack;
    private Button btnNitip;
    private TextView tvGroupName;
    private EditText etTitipDetail;
    private TitipViewModel titipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_titip_detail);

        initializeVariables();
        setValues();
        setListeners();
    }

    private void initializeVariables() {
        firebaseAuth = FirebaseAuth.getInstance();
        btnBack = findViewById(R.id.btnBack);
        btnNitip = findViewById(R.id.btnNitip);
        tvGroupName = findViewById(R.id.tvGroupName);
        etTitipDetail = findViewById(R.id.etTitipDetail);
        titipViewModel = new TitipViewModel();
    }

    private void setValues() {
        String groupName = getIntent().getExtras().getString("GroupName");
        tvGroupName.setText(groupName);
    }

    private void setListeners() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnNitip.setOnClickListener(e -> {
            String titipID = getIntent().getExtras().getString("TitipID");

            String name = firebaseAuth.getCurrentUser().getDisplayName();
            String email = firebaseAuth.getCurrentUser().getEmail();
            String detail = etTitipDetail.getText().toString();

            titipViewModel.addNewTitipDetail(titipID,
                new TitipDetail(new User(name, email, "", ""),
                    detail));

            Toast.makeText(this, "Titip Detail Added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}