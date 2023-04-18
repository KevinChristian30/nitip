package edu.bluejack22_2.nitip.Repository;

import androidx.lifecycle.LifecycleOwner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class GroupRepository {
    private FirebaseAuth fAuth;
    private FirebaseFirestore dbFs;
    private FirebaseUser currUser;
    private UserViewModel userViewModel;
    private LifecycleOwner lifecycleOwner;
    public GroupRepository(LifecycleOwner lifecycleOwner) {
        fAuth = FirebaseAuth.getInstance();
        dbFs = FirebaseFirestore.getInstance();
        currUser = fAuth.getCurrentUser();
        userViewModel = new UserViewModel();
        this.lifecycleOwner = lifecycleOwner;
    }
    public void CreateGroup(String groupName, String groupCode) {
        ArrayList<User> users = new ArrayList<>();
        userViewModel.getUser(currUser.getEmail()).observe(lifecycleOwner, user -> {
            Map<String, Object> data = new HashMap<>();
            users.add(user);
            data.put("group_name", groupName);
            data.put("group_code", groupCode);
            data.put("group_member", users);

            dbFs.collection("groups").add(data);
        });

    }
}
