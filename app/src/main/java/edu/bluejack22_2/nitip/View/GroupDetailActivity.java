package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import edu.bluejack22_2.nitip.R;

public class GroupDetailActivity extends AppCompatActivity {

    private Button btnBack;

    private TextView tvGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        initialize();
        setListener();
        setValues();
    }

    private void initialize() {
        btnBack = findViewById(R.id.btnBack);
        tvGroupName = findViewById(R.id.tvGroupName);
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });
    }

    private void setValues() {
        Bundle extras = getIntent().getExtras();
        tvGroupName.setText(extras.getString("GroupName"));
    }
}