package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import edu.bluejack22_2.nitip.R;

public class GroupDetailActivity extends AppCompatActivity {

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        initialize();
        setListener();
    }

    private void initialize() {
        btnBack = findViewById(R.id.btnBack);
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });
    }
}