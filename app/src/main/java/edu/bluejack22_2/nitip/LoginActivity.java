package edu.bluejack22_2.nitip;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import edu.bluejack22_2.nitip.Database.Database;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference db = Database.getInstance();

    // Android Component
    EditText emailET;
    EditText passwordET;
    Button loginButton;
    Button loginWithGoogleButton;

    // Login Google Component
    private ActivityResultLauncher<Intent> launcher;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        initialize();
//        setListener();
    }

//    private void initialize() {
//        // initialize android component
//        emailET = findViewById(R.id.email_text);
//        passwordET = findViewById(R.id.password_text);
//        loginButton = findViewById(R.id.login_button);
//        loginWithGoogleButton = findViewById(R.id.login_google_button);
//
//        // initialize login google component
//        launcher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    // Handle the successful sign-in here
//                } else {
//                    // Handle the failed sign-in here
//                }
//        });
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//                requestEmail().build();
//        gsc = GoogleSignIn.getClient(this, gso);
//    }

    private void setListener() {
        loginButton.setOnClickListener(e -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();


        });

        loginWithGoogleButton.setOnClickListener(e -> {
            SignInWithGoogle();
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