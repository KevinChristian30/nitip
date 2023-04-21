package edu.bluejack22_2.nitip.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Repository.GroupRepository;

public class GroupViewModel {
    private GroupRepository groupRepository;
    public GroupViewModel(LifecycleOwner lifecycleOwner) {
        groupRepository = new GroupRepository(lifecycleOwner);
    }

    public interface GroupCallback {
        void onHandleGroup(Response response);
    }

    public void CreateGroup(String groupName, String groupCode, GroupCallback callback) {
        Response response = new Response(null);

        if (groupName.trim().isEmpty() || groupCode.trim().isEmpty()) {
            response.setError(new Error("All field must be filled"));
        }
        else if (groupName.trim().length() < 6) {
            response.setError(new Error("Group name length must be more than 5 characters"));
        }
        else if (groupCode.trim().length() < 6) {
            response.setError(new Error("Group code length must be more than 5 characters"));
        }



        groupRepository.CheckCodeExists(groupCode, new GroupRepository.GroupCodeCheckCallback() {
            @Override
            public void onGroupCodeChecked(boolean isUnique) {
                response.setError(new Error("Group code is exists"));
                callback.onHandleGroup(response);
                return;
            }
        });

        if (response.getError() != null) {
            callback.onHandleGroup(response);
            return;
        }

        groupRepository.CreateGroup(groupName, groupCode);

        callback.onHandleGroup(response);
    }



    public void JoinGroup(String groupCode, GroupCallback callback) {

        Response response = new Response(null);

        if (groupCode.trim().isEmpty()) {
            response.setError(new Error("Group code must be filled"));
        }
        if (response.getError() != null) {
            callback.onHandleGroup(response);
            return;
        }
        groupRepository.CheckCodeAndGroupMember(groupCode, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot groupDoc = task.getResult();

                    response.setResponse(groupDoc.get("group_name").toString());
                } else {
                    System.out.println("test");
                    response.setError(new Error(task.getException().getMessage()));
                }

                callback.onHandleGroup(response);
            }
        });

    }
}
