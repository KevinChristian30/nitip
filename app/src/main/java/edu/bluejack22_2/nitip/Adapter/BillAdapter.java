package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.ClickListener.HolderClickListener;
import edu.bluejack22_2.nitip.Model.Bill;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvBillID, tvLenderEmail, tvDebtorEmail, tvAmount, tvStatus, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBillID = itemView.findViewById(R.id.tvBillID);
            tvLenderEmail = itemView.findViewById(R.id.tvLenderEmail);
            tvDebtorEmail = itemView.findViewById(R.id.tvDebtorEmail);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    private Context context;
    private ArrayList<Bill> data;
    private HolderClickListener listener;

    public BillAdapter(Context context, ArrayList<Bill> data, HolderClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    public void updateData(ArrayList<Bill> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Bill bill = data.get(position);

        holder.tvBillID.setText(context.getResources().getString(R.string.bill_id) + bill.getId());
        holder.tvLenderEmail.setText(context.getResources().getString(R.string.lender) + bill.getLender_email());
        holder.tvDebtorEmail.setText(context.getResources().getString(R.string.debtor) + bill.getDebtor_email());
        holder.tvAmount.setText(context.getResources().getString(R.string.amount2) + String.valueOf(bill.getAmount()));
        holder.tvStatus.setText(context.getResources().getString(R.string.status) + bill.getStatus());
        holder.tvDate.setText(context.getResources().getString(R.string.date2) + bill.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
