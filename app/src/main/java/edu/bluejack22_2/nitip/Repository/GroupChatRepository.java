package edu.bluejack22_2.nitip.Repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.bluejack22_2.nitip.Database.Database;

import edu.bluejack22_2.nitip.Model.Message;
import edu.bluejack22_2.nitip.Service.TimeService;

public class GroupChatRepository {
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    public GroupChatRepository() {
        database = FirebaseDatabase.getInstance("https://nitip-6f389-default-rtdb.firebaseio.com/");
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void SendMessage(String newMessage, String groupCode) {
        Message message = new Message(newMessage, firebaseAuth.getCurrentUser().getEmail(), TimeService.getCurrentTime());
        reference.child(groupCode).child("messages").push().setValue(message);
    }

    public void getMessage(MutableLiveData<List<Message>> liveData, String groupCode) {
        System.out.println(groupCode);
        DatabaseReference ref = database.getReference(groupCode).child("messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    messages.add(message);
                }

                liveData.setValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
