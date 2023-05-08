package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.bluejack22_2.nitip.Adapter.BottomNavigationAdapter;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;

public class HomeActivity extends AppCompatActivity {
    private FloatingActionButton btnNitip;
    private ViewPager2 viewPager;
    private BottomNavigationAdapter bottomNavigationAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
        setNavigation();
        setListener();
    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        btnNitip = findViewById(R.id.btnNitip);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationAdapter = new BottomNavigationAdapter(this);
    }

    private void setNavigation() {
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

    private void setListener() {
        btnNitip.setOnClickListener(e -> {
            ActivityChanger.changeActivity(this, NitipActivity.class);
        });
    }

}