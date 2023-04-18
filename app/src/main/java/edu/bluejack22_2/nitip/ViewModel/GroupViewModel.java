package edu.bluejack22_2.nitip.ViewModel;

import androidx.lifecycle.LifecycleOwner;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Repository.GroupRepository;

public class GroupViewModel {
    private GroupRepository groupRepository;
    public GroupViewModel(LifecycleOwner lifecycleOwner) {
        groupRepository = new GroupRepository(lifecycleOwner);
    }

    public interface CreateGroupCallback {
        void onCreateGroup(Response response);
    }

    public void CreateGroup(String groupName, String groupCode, CreateGroupCallback callback) {
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

        if (response.getError() != null) {
            callback.onCreateGroup(response);
            return;
        }

        groupRepository.CheckCodeUnique(groupCode, new GroupRepository.GroupCodeCheckCallback() {
            @Override
            public void onGroupCodeChecked(boolean isUnique) {
                response.setError(new Error("Group code is exists"));
                callback.onCreateGroup(response);
                return;
            }
        });

        groupRepository.CreateGroup(groupName, groupCode);

        callback.onCreateGroup(response);
    }
}
