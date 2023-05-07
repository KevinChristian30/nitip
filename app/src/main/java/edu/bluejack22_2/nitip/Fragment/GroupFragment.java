package edu.bluejack22_2.nitip.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.bluejack22_2.nitip.Adapter.GroupPageAdapter;
import edu.bluejack22_2.nitip.ClickListener.GroupClickListener;
import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.View.CreateNewGroupActivity;
import edu.bluejack22_2.nitip.View.JoinGroupActivity;
import edu.bluejack22_2.nitip.ViewModel.GroupViewModel;


public class GroupFragment extends Fragment {
    GroupPageAdapter adapter;
    RecyclerView recyclerView;
    GroupClickListener listener;
    List<GroupRow> data;
    Button btnJoinGroup;
    View view;
    GroupViewModel groupViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group, container, false);

        initialize(view);
        setList(view);

        setListener(view);

        return view;
    }

    private void initialize(View view) {
        btnJoinGroup = view.findViewById(R.id.btnJoinGroup);
        recyclerView = view.findViewById(R.id.rvGroup);
        data = new ArrayList<>();
        listener = new GroupClickListener() {
            @Override
            public void click(int index) {

            }
        };
        groupViewModel = new GroupViewModel(this);
    }

    private void setList(View view) {
        groupViewModel.getGroupLiveData().observe(getViewLifecycleOwner(), groupRows -> {
            data = groupRows;
            setRecyclerView(view);
        });
    }

    private void setRecyclerView(View view) {
        adapter = new GroupPageAdapter(data, view.getContext(), listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void setListener(View view) {
        btnJoinGroup.setOnClickListener(e -> {
            ActivityChanger.changeActivity(view.getContext(), JoinGroupActivity.class);
        });
    }
}