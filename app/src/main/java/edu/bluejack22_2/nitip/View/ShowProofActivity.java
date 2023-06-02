package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.bluejack22_2.nitip.R;

public class ShowProofActivity extends AppCompatActivity {

    private ImageView ivProof;
    private Button btnBack;
    private TextView tvActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_proof);

        init();
        setListener();
        setComponent();
    }

    private void init() {
        ivProof = findViewById(R.id.ivProof);
        btnBack = findViewById(R.id.btnBack);
        tvActivityTitle = findViewById(R.id.tvActivityTitle);
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });
    }

    private void setComponent() {

        tvActivityTitle.setText(getIntent().getStringExtra("name"));

        String firebaseUri = getIntent().getStringExtra("uri");
        System.out.println(firebaseUri);
        Glide.with(this)
                .load(firebaseUri)
                .into(ivProof);
    }
}