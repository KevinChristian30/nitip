package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Repository.AuthRepository;
import edu.bluejack22_2.nitip.ViewModel.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {
    AuthRepository authRepository;
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

        authRepository = new AuthRepository();
        forgotPasswordViewModel = new ForgotPasswordViewModel();
    }

    private void setListener() {
        btnSendOtp.setOnClickListener(e -> {
            String emailText = etEmail.getText().toString();

            forgotPasswordViewModel.SendResetPasswordRequest(emailText, new ForgotPasswordViewModel.SendResetPasswordRequestCallBack() {
                @Override
                public void onSendOtpResponse(Response response) {
                    if (response.getError() != null) {
                        Toast.makeText(ForgotPasswordActivity.this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityChanger.changeActivity(ForgotPasswordActivity.this, ForgotPasswordConfirmationActivity.class);
                    }
                }
            });
        });
    }
}