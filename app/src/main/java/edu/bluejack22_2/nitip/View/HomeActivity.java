package edu.bluejack22_2.nitip.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.bluejack22_2.nitip.Adapter.BottomNavigationAdapter;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.GoogleService;
import edu.bluejack22_2.nitip.ViewModel.LoginViewModel;

public class HomeActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ViewPager2 viewPager;
    private BottomNavigationAdapter bottomNavigationAdapter;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        viewPager.setAdapter(bottomNavigationAdapter);
        viewPager.setUserInputEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.miDashboard:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.miGroup:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.miHistory:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.miProfile:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });

    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //        logoutBtn = findViewById(R.id.logoutBtn);
        loginViewModel = new LoginViewModel();
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationAdapter = new BottomNavigationAdapter(this);
    }

}