package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    ForgotPasswordViewModel forgotPasswordViewModel;
    private Button btnSendOtp;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialize();
        setListener();
    }

    private void initialize() {
        btnSendOtp = findViewById(R.id.btnSendOtp);
        etEmail = findViewById(R.id.etEmail);

        forgotPasswordViewModel = new ForgotPasswordViewModel();
    }

    private void setListener() {
        btnSendOtp.setOnClickListener(e -> {
            String emailText = etEmail.getText().toString();
        });
    }
}