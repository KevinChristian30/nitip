package edu.bluejack22_2.nitip.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Model.Titip;

public class BillRepository {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth fAuth;

    public BillRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
    }

    public void createBill(Bill bill) {
        HashMap<String, Object> billData = new HashMap<>();

        billData.put("lender_email", bill.getLender_email());
        billData.put("debtor_email", bill.getDebtor_email());
        billData.put("amount", bill.getAmount());
        billData.put("status", bill.getStatus());

        firebaseFirestore.collection("bill").add(billData);
    }

    public void getBillsByEmailAndStatusLiveData(MutableLiveData<List<Bill>> billLiveDatas, String email, String status) {
        Query billsRef = firebaseFirestore.collection("bill");

        billsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Bill> groupList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Bill bill = document.toObject(Bill.class);
                        bill.setId(document.getId());
                        groupList.add(bill);

                    }
                    billLiveDatas.setValue(groupList);
                }
            }

        });
    }
}
