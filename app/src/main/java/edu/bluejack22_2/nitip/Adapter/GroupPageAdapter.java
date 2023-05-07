package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.bluejack22_2.nitip.ClickListener.GroupClickListener;
import edu.bluejack22_2.nitip.Holder.GroupViewHolder;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.R;

public class GroupPageAdapter extends RecyclerView.Adapter<GroupViewHolder> {
    List<GroupRow> list = Collections.emptyList();

    Context context;
    GroupClickListener listener;

    public GroupPageAdapter(List<GroupRow> list, Context context, GroupClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context tempContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(tempContext);

        View transactionView = inflater.inflate(R.layout.group_card, parent, false);

        GroupViewHolder viewHolder = new GroupViewHolder(transactionView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.tvGroupName.setText(list.get(position).getGroupName());
        holder.tvUsername.setText(list.get(position).getUsername());
        holder.tvLastMessage.setText(list.get(position).getLastMessage());
        holder.tvLastTime.setText(list.get(position).getLastTime());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
