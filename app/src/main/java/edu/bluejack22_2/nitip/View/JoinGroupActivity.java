package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.GroupViewModel;

public class JoinGroupActivity extends AppCompatActivity {

    private EditText etGroupCode;
    private Button btnJoinGroup;

    private Button btnCreateGroup;
    private Button btnBack;
    private GroupViewModel groupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        initialize();
        setListener();
    }

    private void initialize() {
        etGroupCode = findViewById(R.id.etGroupCode);
        btnJoinGroup = findViewById(R.id.btnJoinGroup);
        btnBack = findViewById(R.id.btnBack);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);
        groupViewModel = new GroupViewModel(this);
    }

    private void setListener() {
        btnJoinGroup.setOnClickListener(e -> {
            String groupCode = etGroupCode.getText().toString();
            groupViewModel.JoinGroup(this, groupCode, new GroupViewModel.GroupCallback() {
                @Override
                public void onHandleGroup(Response response) {
                    if (response.getError() != null) {
                        Toast.makeText(JoinGroupActivity.this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(JoinGroupActivity.this, getResources().getString(R.string.joined) + response.getResponse().toString(), Toast.LENGTH_SHORT).show();
                        ActivityChanger.changeActivity(JoinGroupActivity.this, HomeActivity.class);
                    }
                }
            });

        });

        btnBack.setOnClickListener(e -> {

            finish();
            ActivityChanger.changeActivity(JoinGroupActivity.this, HomeActivity.class);

        });

        btnCreateGroup.setOnClickListener(e -> {

            ActivityChanger.changeActivity(JoinGroupActivity.this, CreateNewGroupActivity.class);

        });
    }
}