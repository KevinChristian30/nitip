package edu.bluejack22_2.nitip.ViewModel;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Repository.BillRepository;

public class BillViewModel {

    MutableLiveData<List<Bill>> billLiveDatas;
    private BillRepository billRepository;

    public BillViewModel() {
        billRepository = new BillRepository();
        billLiveDatas = new MutableLiveData<>();
    }

    public void createBill(Bill bill) {
        if (bill.getAmount() != 0) {
            billRepository.createBill(bill);
        }
    }

    public MutableLiveData<List<Bill>> getBillsByEmailAndStatus(String email, String status) {
        billRepository.getBillsByEmailAndStatusLiveData(billLiveDatas, email, status);
        return billLiveDatas;
    }
}
