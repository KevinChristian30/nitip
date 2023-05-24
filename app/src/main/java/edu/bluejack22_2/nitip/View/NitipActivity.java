package edu.bluejack22_2.nitip.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Adapter.NitipRecyclerViewAdapter;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.TitipViewModel;

public class NitipActivity extends AppCompatActivity {

    private TitipViewModel titipViewModel;
    private Button btnBack;
    private RecyclerView rvTitip;
    ArrayList<Titip> data;

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
        rvTitip = findViewById(R.id.rvTitip);

        data = new ArrayList<>();
        titipViewModel = new TitipViewModel();
    }

    private void setList() {
        titipViewModel.getTitipLiveData().observe(this, titip -> {
            data = (ArrayList<Titip>) titip;
            setRecyclerView();
        });
    }

    private void setRecyclerView() {
        NitipRecyclerViewAdapter adapter = new NitipRecyclerViewAdapter(data, this);

        rvTitip.setAdapter(adapter);
        rvTitip.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener() {
        btnBack.setOnClickListener(e -> {
            finish();
        });
    }
}