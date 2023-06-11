package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Repository.AuthRepository;
import edu.bluejack22_2.nitip.Service.EmailService;
import edu.bluejack22_2.nitip.Service.RandomService;
import edu.bluejack22_2.nitip.ViewModel.ChangePasswordViewModel;

public class OTPForChangePasswordActivity extends AppCompatActivity {

    private EditText etOTPCode;
    private Button btnSubmit;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private AuthRepository authRepository;
    private ChangePasswordViewModel changePasswordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_change_password);

        initialize();
        sendOTP();
        setListener();
    }

    private void initialize() {
        etOTPCode = findViewById(R.id.etOTPCode);
        btnSubmit = findViewById(R.id.btnSubmitOTP);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        authRepository = new AuthRepository();
        changePasswordViewModel = new ChangePasswordViewModel();
    }

    private void sendOTP() {
        String randomOTP = RandomService.RandomizeOTP();
        new EmailService("nitipkcrg@gmail.com", "jfoxyzupqastmoyk",
                firebaseUser.getEmail(), "Forgot Password OTP", "Your OTP code is " + randomOTP).execute();
        authRepository.SaveOTP(firebaseAuth.getCurrentUser().getEmail(), randomOTP);
    }

    private void setListener() {
        btnSubmit.setOnClickListener(e -> {
            String otp = etOTPCode.getText().toString();

            changePasswordViewModel.checkOTP(this, otp, new ChangePasswordViewModel.CheckOTPCallback() {
                @Override
                public void CheckOTP(Response response) {
                    if (response.getError() != null) {
                        Toast.makeText(OTPForChangePasswordActivity.this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityChanger.changeActivity(OTPForChangePasswordActivity.this, ChangePasswordActivity.class);
                    }
                }
            });
        });
    }
}