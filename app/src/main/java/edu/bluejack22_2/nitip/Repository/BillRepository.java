package edu.bluejack22_2.nitip.Repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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

}
