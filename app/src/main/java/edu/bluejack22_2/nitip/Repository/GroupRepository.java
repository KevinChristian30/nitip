package edu.bluejack22_2.nitip.Repository;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
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

    public interface GroupCodeCheckCallback {
        void onGroupCodeChecked(boolean isUnique);
    }

    public void CheckCodeExists(String groupCode, GroupCodeCheckCallback callback) {
        dbFs.collection("groups").whereEqualTo("group_code", groupCode).get().addOnCompleteListener(e -> {
            if (e.isSuccessful()) {
                callback.onGroupCodeChecked(false);
            }
        });

        callback.onGroupCodeChecked(true);
    }

    public void CheckCodeAndGroupMember(String groupCode, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        TaskCompletionSource<DocumentSnapshot> taskCompletionSource = new TaskCompletionSource<>();

        dbFs.collection("groups").whereEqualTo("group_code", groupCode).get().addOnCompleteListener(e -> {
            if (e.isSuccessful()) {
                QuerySnapshot querySnapshot = e.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    DocumentSnapshot groupDoc = querySnapshot.getDocuments().get(0);
                    List<Map<String, Object>> groupMembers = (List<Map<String, Object>>) groupDoc.get("group_member");
                    boolean isUserMember = false;
                    if (groupMembers != null) {
                        for (Map<String, Object> member : groupMembers) {
                            if (fAuth.getCurrentUser().getEmail().equals(member.get("email"))) {
                                isUserMember = true;
                                break;
                            }
                        }

                        if (isUserMember) {
                            taskCompletionSource.setException(new Exception("You are already a member of the group"));
                        }
                        else {
                            // User is not a member of the group
                            UserRepository userRepository = new UserRepository();
                            LiveData<User> response = (LiveData<User>) userRepository.getUser(fAuth.getCurrentUser().getEmail()).getResponse();

                            response.observe(lifecycleOwner, user -> {
                                Map<String, Object> memberData = new HashMap<>();
                                memberData.put("username", user.getUsername());
                                memberData.put("email", user.getEmail());
                                memberData.put("password", "");
                                memberData.put("profile", user.getProfile());
                                groupMembers.add(memberData);
                                groupDoc.getReference().update("group_member", groupMembers);
                            });

                            taskCompletionSource.setResult(groupDoc);
                        }
                    }
                }
                else {
                    taskCompletionSource.setException(new Exception("Group does not exist"));
                }
            }
            else {
                taskCompletionSource.setException(new Exception("Error checking group code"));
            }
        });
        taskCompletionSource.getTask().addOnCompleteListener(onCompleteListener);
    }

}
