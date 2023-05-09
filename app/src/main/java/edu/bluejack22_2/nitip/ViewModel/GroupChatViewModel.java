package edu.bluejack22_2.nitip.ViewModel;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.bluejack22_2.nitip.Model.Message;
import edu.bluejack22_2.nitip.Repository.GroupChatRepository;

public class GroupChatViewModel {
    GroupChatRepository repository;
    MutableLiveData<List<Message>> messageLiveData;

    public GroupChatViewModel() {
        repository = new GroupChatRepository();
        messageLiveData = new MutableLiveData<>();
    }

    public boolean SendMessage(String newMessage, String groupCode) {
        if (newMessage.trim().isEmpty()) {
            return false;
        }

        repository.SendMessage(newMessage, groupCode);
        return true;
    }

    public void getMessage() {
        repository.getMessage(messageLiveData);
    }

    public MutableLiveData<List<Message>> getMessageLiveData() {
        getMessage();
        return messageLiveData;
    }

}
