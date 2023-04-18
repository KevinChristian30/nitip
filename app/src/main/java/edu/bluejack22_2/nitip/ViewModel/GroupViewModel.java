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

    public Response CreateGroup(String groupName, String groupCode) {
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
            return response;
        }

        groupRepository.CreateGroup(groupName, groupCode);

        return response;
    }
}
