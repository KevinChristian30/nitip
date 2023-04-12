package edu.bluejack22_2.nitip.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.GoogleService;
import edu.bluejack22_2.nitip.ViewModel.LoginViewModel;

public class HomeActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    Button logoutBtn;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.logoutBtn);
        loginViewModel = new LoginViewModel();

        gso = GoogleService.getGso(this);

        gsc = GoogleService.getGsc(this);

        logoutBtn.setOnClickListener(e -> {
            gsc.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loginViewModel.googleLogout();
                    Toast.makeText(HomeActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                }
            });
            ActivityChanger.changeActivity(this, LoginActivity.class);
        });
    }

}