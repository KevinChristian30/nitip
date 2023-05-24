package edu.bluejack22_2.nitip.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Adapter.BillAdapter;
import edu.bluejack22_2.nitip.ClickListener.HolderClickListener;
import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.View.BillDetailActivity;
import edu.bluejack22_2.nitip.ViewModel.BillViewModel;

public class HistoryFragment extends Fragment {

    private Spinner spinTransactionType;
    private RecyclerView rvBills;
    private ArrayList<Bill> bills;
    private BillViewModel billViewModel;
    private FirebaseAuth firebaseAuth;
    private BillAdapter adapter;
    private HolderClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();

        billViewModel.getBillsByEmailAndStatus(firebaseAuth.getCurrentUser().getEmail(),
                "Finished").observe(getViewLifecycleOwner(), data -> {
            bills = (ArrayList<Bill>) data;
            setRecyclerView(getView());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        initializeVariables(view);
        setSpinner();
        setValues(view);

        return view;
    }

    public void initializeVariables(View view) {
        spinTransactionType = view.findViewById(R.id.spinTransactionType);
        rvBills = view.findViewById(R.id.rvBills);

        bills = new ArrayList<>();
        billViewModel = new BillViewModel();

        firebaseAuth = FirebaseAuth.getInstance();

        listener = new HolderClickListener() {
            @Override
            public void click(int index) {
                Intent intent = new Intent(getActivity(), BillDetailActivity.class);
                intent.putExtra("Bill", bills.get(index).getDate());
                intent.putExtra("Bill", bills.get(index).getDebtor_email());
                intent.putExtra("Bill", bills.get(index).getAmount());
                intent.putExtra("Bill", bills.get(index).getLender_email());
                intent.putExtra("Bill", bills.get(index).getId());
                intent.putExtra("Bill", bills.get(index).getStatus());
                startActivity(intent);
            }
        };

        adapter = new BillAdapter(getContext(), bills, listener);
    }

    private void setSpinner() {
        String[] items = new String[3];
        items[0] = "All";
        items[1] = "Debted";
        items[2] = "Lended";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTransactionType.setAdapter(adapter);
    }

    private void setValues(View view) {
        billViewModel.getBillsByEmailAndStatus(firebaseAuth.getCurrentUser().getEmail(),
                "Finished").observe(getViewLifecycleOwner(), data -> {
            bills = (ArrayList<Bill>) data;
            setRecyclerView(view);
            setSpinnerListener();
        });
    }

    private void setRecyclerView(View view) {
        rvBills.setAdapter(adapter);
        adapter.updateData(bills);
        rvBills.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setSpinnerListener() {
        spinTransactionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Bill> filteredBill = new ArrayList<>();
                for (Bill bill : bills) {
                    if (adapterView.getItemAtPosition(i).toString().equals("All") &&
                            (bill.getDebtor_email().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) ||
                                    bill.getLender_email().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())    )) {
                        filteredBill.add(bill);
                    }
                    if (adapterView.getItemAtPosition(i).toString().equals("Debted") &&
                            bill.getDebtor_email().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        filteredBill.add(bill);
                    }
                    else if (adapterView.getItemAtPosition(i).toString().equals("Lended") &&
                            bill.getLender_email().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        filteredBill.add(bill);
                    }

                }

                adapter.updateData(filteredBill);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}