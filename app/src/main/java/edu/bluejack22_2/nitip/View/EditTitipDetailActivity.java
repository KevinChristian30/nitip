package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import edu.bluejack22_2.nitip.Adapter.NitipDetailAdapter;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;
import kotlin.time.TestTimeSource;

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

            titipViewModel.updateTitipDetail(titipID, email, newDetail, this);
            
            finish();
        });

        btnDelete.setOnClickListener(e -> {

        });
    }

}