package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.bluejack22_2.nitip.Adapter.GroupChatAdapter;
import edu.bluejack22_2.nitip.Adapter.GroupPageAdapter;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Model.Message;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.GroupChatViewModel;

public class GroupDetailActivity extends AppCompatActivity {

    private GroupChatViewModel groupChatViewModel;

    private Button btnBack;

    private Button btnGoToNitipPage;

    private Button btnSendMessage;

    private TextView tvGroupName;

    private EditText etNewMessage;

    private GroupChatAdapter adapter;

    private RecyclerView rvChatRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        initialize();
        setListener();
        setValues();
        getMessage();
    }

    private void initialize() {
        groupChatViewModel = new GroupChatViewModel();
        btnBack = findViewById(R.id.btnBack);
        tvGroupName = findViewById(R.id.tvGroupName);
        btnGoToNitipPage = findViewById(R.id.btnGoToTitipPage);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        etNewMessage = findViewById(R.id.etNewMessage);
        rvChatRoom = findViewById(R.id.rvChatRoom);

    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnGoToNitipPage.setOnClickListener(e -> {
            Intent intent = new Intent(this, CreateNewTitipActivity.class);

            Bundle extras = getIntent().getExtras();
            intent.putExtra("GroupCode", extras.getString("GroupCode"));
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
}