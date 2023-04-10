package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameET;
    EditText emailET;
    EditText passwordET;
    EditText confPasswordET;
    Button registerBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();
        setListener();
    }

    private void initialize() {
        usernameET = findViewById(R.id.etUsername);
        emailET = findViewById(R.id.etEmail);
        passwordET = findViewById(R.id.etPassword);
        confPasswordET = findViewById(R.id.etConfirmPassword);
        registerBtn = findViewById(R.id.btnRegister);
        loginBtn = findViewById(R.id.btnLogin);
    }

    private void setListener() {
        registerBtn.setOnClickListener(e -> {
            String usernameText = usernameET.getText().toString();
            String emailText = emailET.getText().toString();
            String passwordText = passwordET.getText().toString();
            String confPasswordText = confPasswordET.getText().toString();


        });

        loginBtn.setOnClickListener(e -> {
            ActivityChanger.changeActivity(this, LoginActivity.class);
        });
    }

}