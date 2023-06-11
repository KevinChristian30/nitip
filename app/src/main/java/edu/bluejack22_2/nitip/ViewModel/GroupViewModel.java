package edu.bluejack22_2.nitip.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Repository.GroupRepository;

public class GroupViewModel {
    MutableLiveData<List<GroupRow>> groupLiveData;

    ArrayList<GroupRow> groups;
    private GroupRepository groupRepository;
    public GroupViewModel(LifecycleOwner lifecycleOwner) {
        groupRepository = new GroupRepository(lifecycleOwner);
        groupLiveData = new MutableLiveData<>();
        groups = new ArrayList<>();
    }

    public interface GroupCallback {
        void onHandleGroup(Response response);
    }

    public void CreateGroup(Context context, String groupName, String groupCode, GroupCallback callback) {
        Response response = new Response(null);

        if (groupName.trim().isEmpty() || groupCode.trim().isEmpty()) {
            response.setError(new Error(context.getResources().getString(R.string.field_must_filled)));
        }
        else if (groupName.trim().length() < 6) {
            response.setError(new Error(context.getResources().getString(R.string.group_name_length)));
        }
        else if (groupCode.trim().length() < 6) {
            response.setError(new Error(context.getResources().getString(R.string.group_code_length)));
        }

        if (response.getError() != null) {
            callback.onHandleGroup(response);
            return;
        }

        groupRepository.CheckCodeExists(groupCode, new GroupRepository.GroupCodeCheckCallback() {
            @Override
            public void onGroupCodeChecked(boolean isUnique) {
                if (!isUnique) {
                    response.setError(new Error(context.getResources().getString(R.string.group_code_exist)));
                }
                else {
                    groupRepository.CreateGroup(groupName, groupCode);
                }
                callback.onHandleGroup(response);
                return;
            }
        });

    }

    public void JoinGroup(Context context, String groupCode, GroupCallback callback) {

        Response response = new Response(null);

        if (groupCode.trim().isEmpty()) {
            response.setError(new Error(context.getResources().getString(R.string.group_code_must_filled)));
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
                    response.setError(new Error(task.getException().getMessage()));
                }

                callback.onHandleGroup(response);
            }
        });

    }

    public MutableLiveData<List<GroupRow>> getGroupLiveData() {
        getGroupData();
        return groupLiveData;
    }

    public void getGroupData() {
        groupRepository.getGroupData(groupLiveData);
    }

}
