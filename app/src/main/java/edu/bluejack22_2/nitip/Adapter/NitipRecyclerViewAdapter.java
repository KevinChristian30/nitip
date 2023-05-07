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

        holder.getTitipNameTV().setText(currentTitip.getTitipName());
        holder.getEntrusterEmailTV().setText(currentTitip.getEntrusterEmail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitipName;
        private final TextView tvEntrusterEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitipName = itemView.findViewById(R.id.tvTitipName);
            tvEntrusterEmail = itemView.findViewById(R.id.tvEntrusterEmail);
        }

        public TextView getTitipNameTV() {
            return this.tvTitipName;
        }

        public TextView getEntrusterEmailTV() {
            return this.tvEntrusterEmail;
        }

    }

}
