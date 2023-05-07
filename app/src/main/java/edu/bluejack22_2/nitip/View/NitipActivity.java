package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;

public class NitipActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnCreateNewTitip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitip);

        initialize();
        setListener();
    }

    private void initialize() {
        btnBack = findViewById(R.id.btnBack);
        btnCreateNewTitip = findViewById(R.id.btnCreateNewTitip);
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnCreateNewTitip.setOnClickListener(e -> {
            ActivityChanger.changeActivity(this, CreateNewTitipActivity.class);
        });
    }
}