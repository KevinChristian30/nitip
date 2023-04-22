package edu.bluejack22_2.nitip.ViewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.net.URI;

import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.Repository.UserRepository;

public class UserViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public void init(String userEmail) {
        if (userLiveData != null) {
            return;
        }
        userLiveData = (MutableLiveData<User>) userRepository.getUser(userEmail).getResponse();
    }

    public LiveData<User> getUser(String userEmail) {
        init(userEmail);
        return userLiveData;
    }

    public void setUser(User newUser) {
        userLiveData.setValue(newUser);
    }

    public void changeProfilePicture(Uri imageURI) {
        userRepository.ChangeProfilePicture(imageURI, new UserRepository.OnProfilePictureUpdatedListener() {
            @Override
            public void onSuccess(User user) {
                setUser(user);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
