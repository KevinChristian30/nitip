package edu.bluejack22_2.nitip.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import edu.bluejack22_2.nitip.Database.Database;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.View.HomeActivity;
import edu.bluejack22_2.nitip.View.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference db = Database.getInstance();

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnLoginWithGoogle;
    private Button btnRegister;

    private ActivityResultLauncher<Intent> launcher;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        setListener();
    }

    private void initialize() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);
        btnRegister = findViewById(R.id.btnRegister);

        launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Handle the successful sign-in here
                } else {
                    // Handle the failed sign-in here
                }
        });

        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);
    }

    private void setListener() {
        btnLogin.setOnClickListener(e -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
        });

        btnLoginWithGoogle.setOnClickListener(e -> {
            SignInWithGoogle();
        });

        btnRegister.setOnClickListener(e -> {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            startActivity(intent);
            ActivityChanger.changeActivity(this, RegisterActivity.class);
        });
    }

    private void SignInWithGoogle() {
        Intent intent = gsc.getSignInIntent();
        launcher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(this, HomeActivity.class);
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

}