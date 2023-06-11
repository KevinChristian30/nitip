package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import edu.bluejack22_2.nitip.Adapter.GroupChatAdapter;
import edu.bluejack22_2.nitip.Model.Message;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.GroupChatViewModel;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class GroupDetailActivity extends AppCompatActivity {

    private GroupChatViewModel groupChatViewModel;

    private TitipViewModel titipViewModel;

    private Button btnBack;

    private Button btnGoToNitipPage;

    private Button btnSendMessage;

    private TextView tvGroupName;

    private EditText etNewMessage;

    private GroupChatAdapter adapter;

    private RecyclerView rvChatRoom;
    private TextView tvLastTitip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        initialize();
        setLastNitip();
        setListener();
        setValues();
        getMessage();
    }

    private void initialize() {
        groupChatViewModel = new GroupChatViewModel();
        titipViewModel = new TitipViewModel();
        btnBack = findViewById(R.id.btnBack);
        tvGroupName = findViewById(R.id.tvGroupName);
        btnGoToNitipPage = findViewById(R.id.btnGoToTitipPage);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        etNewMessage = findViewById(R.id.etNewMessage);

        rvChatRoom = findViewById(R.id.rvChatRoom);
        tvLastTitip = findViewById(R.id.tvLastTitip);

    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnGoToNitipPage.setOnClickListener(e -> {
            Intent intent = new Intent(this, CreateNewTitipActivity.class);
            Bundle extras = getIntent().getExtras();
            intent.putExtra("GroupCode", extras.getString("GroupCode"));
            intent.putExtra("GroupName", extras.getString("GroupName"));
            startActivity(intent);
        });

        btnSendMessage.setOnClickListener(e -> {
            Intent intent = getIntent();
            String newMessage = etNewMessage.getText().toString();
            String groupCode = intent.getStringExtra("GroupCode");

            if (groupChatViewModel.SendMessage(newMessage, groupCode)) {
                etNewMessage.setText("");
            }
        });
    }

    private void setValues() {
        Bundle extras = getIntent().getExtras();
        tvGroupName.setText(extras.getString("GroupName"));
    }

    private void getMessage() {
        Bundle extras = getIntent().getExtras();
        String groupCode = extras.getString("GroupCode");
        System.out.println(groupCode);
        groupChatViewModel.getMessageLiveData(groupCode).observe(this, data -> {
            setRecyclerView(data);
        });
    }

    private void setRecyclerView(List<Message> data) {
        adapter = new GroupChatAdapter(data, this, this);
        rvChatRoom.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rvChatRoom.setLayoutManager(linearLayoutManager);

    }

    private void setLastNitip() {
        Bundle extras = getIntent().getExtras();
        String groupName = extras.getString("GroupCode");
        titipViewModel.getLastTitip(groupName).observe(this, response -> {
            if (response.getResponse() != null) {
//                System.out.println(response.getResponse().toString());
                tvLastTitip.setText(getResources().getString(R.string.ongoing_nitip2) + response.getResponse().toString());
            }
        });
    }
}