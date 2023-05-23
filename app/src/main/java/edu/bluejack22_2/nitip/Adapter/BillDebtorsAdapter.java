package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.View.BillDebtorsActivity;

public class BillDebtorsAdapter extends RecyclerView.Adapter<BillDebtorsAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llTransaction;
        TextView tvDebtorEmail;
        EditText etAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llTransaction = itemView.findViewById(R.id.llTransaction);
            tvDebtorEmail = itemView.findViewById(R.id.tvDebtorEmail);
            etAmount = itemView.findViewById(R.id.etAmount);
        }

    }

    private Context context;
    private ArrayList<TitipDetail> data;
    private ArrayList<Bill> bills;

    public BillDebtorsAdapter(Context context, ArrayList<TitipDetail> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_transaction_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TitipDetail currentTitipDetail = data.get(position);

        holder.tvDebtorEmail.setText(currentTitipDetail.getUser().getEmail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
