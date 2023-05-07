package edu.bluejack22_2.nitip.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class DashboardFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    UserViewModel userViewModel;
    TextView tvTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(view);
        setTitle(view);

        return view;
    }

    private void init(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        userViewModel = new UserViewModel();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setTitle(View view) {

        userViewModel.getUser(firebaseAuth.getCurrentUser().getEmail()).observe(getViewLifecycleOwner(), user -> {
            tvTitle.setText("Hello, " + user.getUsername());
        });
    }
}