package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.ChangePasswordViewModel;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnChangePassword;
    private ChangePasswordViewModel changePasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialize();
        setListener();
    }

    private void initialize() {
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        changePasswordViewModel = new ChangePasswordViewModel();
    }

    private void setListener() {
        btnChangePassword.setOnClickListener(e -> {
            String password = etPassword.getText().toString();
            String confPassword = etConfirmPassword.getText().toString();

            if (password.trim().length() == 0 || confPassword.trim().length() == 0) {
                Toast.makeText(this, getResources().getString(R.string.password_conf_validation), Toast.LENGTH_SHORT).show();
                return;
            }

            Response response = changePasswordViewModel.ChangePassword(this, password, confPassword);

            if (response.getError() != null) {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, getResources().getString(R.string.remember_password), Toast.LENGTH_SHORT).show();
                ActivityChanger.changeActivity(this, HomeActivity.class);
            }
        });
    }
}