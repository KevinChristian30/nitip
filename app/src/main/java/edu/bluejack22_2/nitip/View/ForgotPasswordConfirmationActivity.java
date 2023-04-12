package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;

public class ForgotPasswordConfirmationActivity extends AppCompatActivity {

    Button btnBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_confirmation);

        initialize();
        setListener();
    }

    private void initialize() {
        btnBackLogin = findViewById(R.id.btnBackLogin);
    }

    private void setListener() {
        btnBackLogin.setOnClickListener(e -> {
            ActivityChanger.changeActivity(this, LoginActivity.class);
        });
    }
}