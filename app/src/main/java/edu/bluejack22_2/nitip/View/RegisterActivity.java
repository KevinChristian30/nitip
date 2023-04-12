package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;
    private EditText usernameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText confPasswordET;
    private Button registerBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();
        setListener();
    }

    private void initialize() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
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
            Response response = viewModel.RegisterUser(this, new User(usernameText, emailText, passwordText), confPasswordText);
            if (response.getError() != null) {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show();
                ActivityChanger.changeActivity(this, LoginActivity.class);
            }
        });

        loginBtn.setOnClickListener(e -> {
            ActivityChanger.changeActivity(this, LoginActivity.class);
        });
    }

}