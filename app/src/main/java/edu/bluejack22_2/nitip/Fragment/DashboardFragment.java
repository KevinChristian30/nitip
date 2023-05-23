package edu.bluejack22_2.nitip.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class DashboardFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    UserViewModel userViewModel;
    TextView tvTitle;
    Spinner spinTransactionType;

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
        setSpinner();

        return view;
    }

    private void init(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        spinTransactionType = view.findViewById(R.id.spinTransactionType);
        userViewModel = new UserViewModel();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setTitle(View view) {

        userViewModel.getUser(firebaseAuth.getCurrentUser().getEmail()).observe(getViewLifecycleOwner(), user -> {
            tvTitle.setText("Hello, " + user.getUsername());
        });
    }

    private void setSpinner() {
        String[] items = new String[2];
        items[0] = "Debted";
        items[1] = "Lended";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTransactionType.setAdapter(adapter);
    }
}