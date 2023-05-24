package edu.bluejack22_2.nitip.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import edu.bluejack22_2.nitip.Model.Bill;

public class BillService {

    public static HashMap<String, String> getUserBillStatistics(ArrayList<Bill> bills) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        int totalDebt = 0, totalReceivable = 0;
        for (Bill bill : bills) {
            if (auth.getCurrentUser().getEmail().equals(bill.getLender_email())) {
                totalReceivable += bill.getAmount();
            } else if(auth.getCurrentUser().getEmail().equals(bill.getDebtor_email())) {
                totalDebt += bill.getAmount();
            }
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        HashMap<String, String> statistics = new HashMap<>();
        statistics.put("TransactionCount", String.valueOf(bills.size()));
        statistics.put("TotalDebt", String.valueOf(numberFormat.format(totalDebt)));
        statistics.put("TotalReceivable", String.valueOf(numberFormat.format(totalReceivable)));

        return statistics;
    }

}
