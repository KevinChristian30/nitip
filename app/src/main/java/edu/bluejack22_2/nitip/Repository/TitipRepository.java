package edu.bluejack22_2.nitip.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bluejack22_2.nitip.Model.Group;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.User;

public class TitipRepository {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth fAuth;

    public TitipRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
    }

    public void CreateTitip(Titip titip) {
        HashMap<String, Object> titipData = new HashMap<>();

        titipData.put("titip_name", titip.getTitipName());
        titipData.put("close_time", titip.getCloseTime());
        titipData.put("titip_detail", titip.getTitipDetails());
        titipData.put("entruster_email", titip.getEntrusterEmail());

        firebaseFirestore.collection("titip").add(titipData);
    }

    public void GetTitip(MutableLiveData<List<Titip>> titipLiveData) {
        Query groupsRef = firebaseFirestore.collection("groups");
        groupsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<GroupRow> groupList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to a Group object
                        Group group = document.toObject(Group.class);
                        for (User user : group.getGroup_member()) {
                            if (user.getEmail().equals(fAuth.getCurrentUser().getEmail())) {
                                groupList.add(new GroupRow(group.getGroup_name(), "test", "test", "test"));
                            }
                        }

                    }



//                    titipLiveData.setValue(groupList);
                }
            }
        });

    }

}
