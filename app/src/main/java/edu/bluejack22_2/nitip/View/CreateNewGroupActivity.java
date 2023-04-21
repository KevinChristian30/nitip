package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.RandomService;
import edu.bluejack22_2.nitip.ViewModel.GroupViewModel;

public class CreateNewGroupActivity extends AppCompatActivity {
    private EditText etGroupName;
    private EditText etGroupCode;
    private Button btnCreateGroup;
    private Button btnRandomizeCode;
    private GroupViewModel groupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);

        initialize();
        setListener();
    }

    private void initialize() {
        etGroupName = findViewById(R.id.etGroupName);
        etGroupCode = findViewById(R.id.etGroupCode);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);
        btnRandomizeCode = findViewById(R.id.btnRandomizeCode);
        groupViewModel = new GroupViewModel(this);
    }

    private void setListener() {
        btnCreateGroup.setOnClickListener(e -> {
            String groupName = etGroupName.getText().toString();
            String groupCode = etGroupCode.getText().toString();

            groupViewModel.CreateGroup(groupName, groupCode, new GroupViewModel.GroupCallback() {
                @Override
                public void onHandleGroup(Response response) {
                    if (response.getError() != null) {
                        Toast.makeText(CreateNewGroupActivity.this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityChanger.changeActivity(CreateNewGroupActivity.this, HomeActivity.class);
                    }
                }
            });


        });

        btnRandomizeCode.setOnClickListener(e -> {
            etGroupCode.setText(RandomService.RandomizeGroupCode());
        });
    }
}