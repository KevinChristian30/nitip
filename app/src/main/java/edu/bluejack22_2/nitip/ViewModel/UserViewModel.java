package edu.bluejack22_2.nitip.ViewModel;

import androidx.lifecycle.LiveData;

import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.Repository.UserRepository;

public class UserViewModel {
    private UserRepository userRepository;
    private LiveData<User> userLiveData;

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public void init(String userEmail) {
        if (userLiveData != null) {
            return;
        }
        userLiveData = (LiveData<User>) userRepository.getUser(userEmail).getResponse();
    }

    public LiveData<User> getUser(String userEmail) {
        init(userEmail);
        return userLiveData;
    }

}
