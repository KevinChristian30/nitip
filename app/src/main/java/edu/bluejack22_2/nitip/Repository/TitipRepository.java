package edu.bluejack22_2.nitip.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bluejack22_2.nitip.Model.Titip;

public class TitipRepository {

    FirebaseFirestore firebaseFirestore;

    public TitipRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void CreateTitip(Titip titip) {
        HashMap<String, Object> titipData = new HashMap<>();

        titipData.put("titip_name", titip.getTitipName());
        titipData.put("close_time", titip.getCloseTime());
        titipData.put("titip_detail", titip.getTitipDetails());
        titipData.put("entruster_email", titip.getEntruster_email());
        titipData.put("group_code", titip.getGroup_code());

        firebaseFirestore.collection("titip").add(titipData);
    }

    public void getTitips(MutableLiveData<List<Titip>> data) {

        Query titipsRef = firebaseFirestore.collection("titip");
        titipsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Titip> groupList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Titip titip = document.toObject(Titip.class);

                        // Check if the person is in the same group as the titip group

                        groupList.add(titip);

                    }
                    data.setValue(groupList);
                }
            }
        });
    }

}
