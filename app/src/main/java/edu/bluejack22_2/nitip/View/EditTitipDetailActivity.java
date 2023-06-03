package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class EditTitipDetailActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnUpdate;
    private Button btnDelete;
    private EditText etTitipDetail;
    private TitipViewModel titipViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_titip_detail);

        initializeVariables();
        setValues();
        setListener();
    }

    private void initializeVariables() {
        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.btnUpdateTitip);
        btnDelete = findViewById(R.id.btnDeleteTitip);
        etTitipDetail = findViewById(R.id.etTitipDetail);

        titipViewModel = new TitipViewModel();
    }

    private void setValues() {
        etTitipDetail.setText(getIntent().getExtras().get("detail").toString());
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnUpdate.setOnClickListener(e -> {
            Intent intent = getIntent();
            String titipID, email, newDetail;

            titipID = intent.getStringExtra("titipID");
            email = intent.getStringExtra("email");
            newDetail = etTitipDetail.getText().toString();

            if (newDetail.trim().isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.fill_detail), Toast.LENGTH_SHORT);
            }

            titipViewModel.updateTitipDetail(titipID, email, newDetail, this);
            
            finish();
        });

        btnDelete.setOnClickListener(e -> {
            Intent intent = getIntent();
            String titipID, email;

            titipID = intent.getStringExtra("titipID");
            email = intent.getStringExtra("email");
            titipViewModel.removeTitipDetail(titipID, email);

            Toast.makeText(this, getResources().getString(R.string.titip_deleted), Toast.LENGTH_SHORT);

            finish();
        });
    }

}