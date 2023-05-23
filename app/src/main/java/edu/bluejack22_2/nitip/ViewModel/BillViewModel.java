package edu.bluejack22_2.nitip.ViewModel;

import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Repository.BillRepository;

public class BillViewModel {

    private BillRepository billRepository;

    public BillViewModel() {
        billRepository = new BillRepository();
    }

    public void createBill(Bill bill) {
        billRepository.createBill(bill);
    }

}
