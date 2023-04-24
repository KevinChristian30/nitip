package edu.bluejack22_2.nitip.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.View.CreateNewGroupActivity;
import edu.bluejack22_2.nitip.View.JoinGroupActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {
    Button btnJoinGroup;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group, container, false);

        initialize(view);
        setListener(view);

        return view;
    }

    private void initialize(View view) {
        btnJoinGroup = view.findViewById(R.id.btnJoinGroup);
    }

    private void setListener(View view) {
        btnJoinGroup.setOnClickListener(e -> {
            ActivityChanger.changeActivity(view.getContext(), JoinGroupActivity.class);
        });
    }
}