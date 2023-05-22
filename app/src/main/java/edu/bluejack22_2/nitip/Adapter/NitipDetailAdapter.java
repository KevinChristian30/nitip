package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.R;

public class NitipDetailAdapter extends RecyclerView.Adapter<NitipDetailAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEmail, tvTitipDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTitipDetail = itemView.findViewById(R.id.tvTitipDetail);
        }

    }

    private Context context;
    private ArrayList<TitipDetail> data;

    public NitipDetailAdapter(Context context, ArrayList<TitipDetail> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nitip_detail_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitipDetail.setText(data.get(position).getDetail());
        holder.tvEmail.setText(data.get(position).getUser().getEmail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
