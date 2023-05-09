package edu.bluejack22_2.nitip.Repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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

    public void getMessage(MutableLiveData<List<Message>> liveData) {

    }
}
