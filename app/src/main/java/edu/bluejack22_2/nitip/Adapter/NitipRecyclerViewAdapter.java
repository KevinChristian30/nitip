package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.R;

public class NitipRecyclerViewAdapter extends RecyclerView.Adapter<NitipRecyclerViewAdapter.ViewHolder>{

    public ArrayList<Titip> data;

    Context context;

    public NitipRecyclerViewAdapter(ArrayList<Titip> data, Context context){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public NitipRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nitip_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NitipRecyclerViewAdapter.ViewHolder holder, int position) {
        Titip currentTitip = data.get(position);

//        holder.getTvGroupName().setText(currentTitip);
        holder.getTvTitipName().setText(currentTitip.getTitipName());
        holder.getTvCreatorName().setText(currentTitip.getEntrusterEmail());
        holder.getTvCloseTime().setText(currentTitip.getCloseTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvGroupName;
        private final TextView tvTitipName;
        private final TextView tvCreatorName;
        private final TextView tvFee;
        private final TextView tvCloseTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvTitipName = itemView.findViewById(R.id.tvTitipName);
            tvCreatorName = itemView.findViewById(R.id.tvCreatorName);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvCloseTime = itemView.findViewById(R.id.tvCloseTime);
        }

        public TextView getTvGroupName() {
            return tvGroupName;
        }

        public TextView getTvTitipName() {
            return tvTitipName;
        }

        public TextView getTvCreatorName() {
            return tvCreatorName;
        }

        public TextView getTvFee() {
            return tvFee;
        }

        public TextView getTvCloseTime() {
            return tvCloseTime;
        }
    }

}
