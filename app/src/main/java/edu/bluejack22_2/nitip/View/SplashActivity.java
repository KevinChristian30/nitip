package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.ivLogo);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_decrease_anim));
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_increase_anim));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser() == null) {
                    ActivityChanger.changeActivity(SplashActivity.this, LoginActivity.class);
                }
                else {
                    ActivityChanger.changeActivity(SplashActivity.this, HomeActivity.class);
                }
                finish();
            }
        }, 3000);
    }
}