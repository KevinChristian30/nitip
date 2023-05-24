package edu.bluejack22_2.nitip.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Adapter.BillAdapter;
import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.BillViewModel;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class DashboardFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    UserViewModel userViewModel;
    BillViewModel billViewModel;
    TextView tvTitle;
    RecyclerView rvBills;
    ArrayList<Bill> bills;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(view);
        setValues(view);

        return view;
    }

    private void init(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        rvBills = view.findViewById(R.id.rvBills);
        userViewModel = new UserViewModel();
        billViewModel = new BillViewModel();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setValues(View view) {
        userViewModel.getUser(firebaseAuth.getCurrentUser().getEmail()).observe(getViewLifecycleOwner(), user -> {
            tvTitle.setText("Hello, " + user.getUsername());
        });

        billViewModel.getBillsByEmailAndStatus("", "").observe(getViewLifecycleOwner(), data -> {
            bills = (ArrayList<Bill>) data;
            setRecyclerView(view);
        });
    }

    private void setRecyclerView(View view) {
        BillAdapter adapter = new BillAdapter(getContext(), bills);
        rvBills.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        rvBills.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}