package edu.bluejack22_2.nitip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;

import edu.bluejack22_2.nitip.Facade.DownloadImageTask;
import edu.bluejack22_2.nitip.Holder.GroupChatHolder;
import edu.bluejack22_2.nitip.Holder.GroupViewHolder;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.Model.Message;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatHolder> {
    private static final int VIEW_TYPE_MY_MESSAGE = 1;
    private static final int VIEW_TYPE_MEMBER_MESSAGE= 0;
    List<Message> list = Collections.emptyList();
    Context context;
    LifecycleOwner lifecycleOwner;

    public GroupChatAdapter(List<Message> list, Context context, LifecycleOwner lifecycleOwner) {
        this.list = list;
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public GroupChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context tempContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(tempContext);

        View transactionView;
        if (viewType == VIEW_TYPE_MY_MESSAGE) {
            transactionView = inflater.inflate(R.layout.user_message, parent, false);
        } else {
            transactionView = inflater.inflate(R.layout.member_message, parent, false);
        }

        GroupChatHolder viewHolder = new GroupChatHolder(transactionView);

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = list.get(position);
        if (message.getSenderEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            return VIEW_TYPE_MY_MESSAGE;
        } else {
            return VIEW_TYPE_MEMBER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(GroupChatHolder holder, int position) {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.getUser(list.get(position).getSenderEmail()).observe(lifecycleOwner, user -> {
            Glide.with(context).load(user.getProfile()).into(holder.ivProfilePicture);
            String imageUrl = user.getProfile();
            new DownloadImageTask(holder.ivProfilePicture).execute(imageUrl);

            holder.tvSender.setText(user.getUsername());

        });

        holder.tvMessage.setText(list.get(position).getMessage());
        holder.tvTimeStamp.setText(list.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
