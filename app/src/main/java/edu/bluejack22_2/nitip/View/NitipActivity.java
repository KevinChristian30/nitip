package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import edu.bluejack22_2.nitip.Adapter.GroupPageAdapter;
import edu.bluejack22_2.nitip.Adapter.NitipRecyclerViewAdapter;
import edu.bluejack22_2.nitip.ClickListener.GroupClickListener;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Model.Group;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.R;

public class NitipActivity extends AppCompatActivity {

    private Button btnBack;
    private Button btnCreateNewTitip;
    private RecyclerView rvTitip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitip);

        initialize();
        setList();
        setListener();
    }

    private void initialize() {
        btnBack = findViewById(R.id.btnBack);
        btnCreateNewTitip = findViewById(R.id.btnCreateNewTitip);

        rvTitip = findViewById(R.id.rvTitip);
    }

    private void setList() {
        ArrayList<Titip> data = new ArrayList<>();

        data.add(new Titip("Test 1", "Test Waktu 1", new ArrayList<>()));
        data.add(new Titip("Test 2", "Test Waktu 2", new ArrayList<>()));
        data.add(new Titip("Test 3", "Test Waktu 3", new ArrayList<>()));

        NitipRecyclerViewAdapter adapter = new NitipRecyclerViewAdapter(data, this);

        rvTitip.setAdapter(adapter);
        rvTitip.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });

        btnCreateNewTitip.setOnClickListener(e -> {
            ActivityChanger.changeActivity(this, CreateNewTitipActivity.class);
        });
    }
}