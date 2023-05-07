package edu.bluejack22_2.nitip.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.bluejack22_2.nitip.R;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    public TextView tvGroupName;
    public TextView tvUsername;
    public TextView tvLastMessage;
    public TextView tvLastTime;
    public View view;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        tvLastMessage = (TextView) itemView.findViewById(R.id.tvLastMessage);
        tvLastTime = (TextView) itemView.findViewById(R.id.tvLastTime);
        view =itemView;
    }
}
