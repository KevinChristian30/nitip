package edu.bluejack22_2.nitip.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.bluejack22_2.nitip.R;

public class GroupChatHolder extends RecyclerView.ViewHolder {

    public ImageView ivProfilePicture;
    public TextView tvSender;
    public TextView tvMessage;
    public TextView tvTimeStamp;
    public View view;

    public GroupChatHolder(@NonNull View itemView) {
        super(itemView);

        ivProfilePicture = (ImageView) itemView.findViewById(R.id.ivProfilePicture);
        tvSender = (TextView) itemView.findViewById(R.id.tvSender);
        tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);

        view = itemView;
    }
}
