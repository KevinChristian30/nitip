package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.View.EditTitipDetailActivity;

public class NitipDetailAdapter extends RecyclerView.Adapter<NitipDetailAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llNitipDetailCard;
        public TextView tvEmail, tvTitipDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llNitipDetailCard = itemView.findViewById(R.id.llNitipDetailCard);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTitipDetail = itemView.findViewById(R.id.tvTitipDetail);
        }

    }

    private String titipID;
    private Context context;
    public static ArrayList<TitipDetail> data;

    public void notifyChanges() {
        this.notifyDataSetChanged();
    }

    public NitipDetailAdapter(Context context, ArrayList<TitipDetail> data, String titipID) {
        this.context = context;
        this.data = data;
        this.titipID = titipID;
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

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        holder.llNitipDetailCard.setOnClickListener(e -> {
            if (data.get(position).getUser().getEmail().equals(firebaseAuth.getCurrentUser().getEmail())) {
                Intent next = new Intent(holder.itemView.getContext(), EditTitipDetailActivity.class);

                next.putExtra("detail", data.get(position).getDetail());
                next.putExtra("email", data.get(position).getUser().getEmail());
                next.putExtra("titipID", titipID);

                context.startActivity(next);
            } else {
                Toast.makeText(holder.itemView.getContext(), "This is not your Titip", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
