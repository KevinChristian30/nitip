package edu.bluejack22_2.nitip.Repository;

import android.util.Log;

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

        titipData.put("titip_name", titip.getTitip_name());
        titipData.put("close_time", titip.getClose_time());
        titipData.put("titip_detail", titip.getTitip_detail());
        titipData.put("entruster_email", titip.getEntruster_email());
        titipData.put("group_code", titip.getGroup_code());
        titipData.put("group_name", titip.getGroup_name());

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
                        Log.e("debug", titip.getTitip_name());

                        groupList.add(titip);

                    }
                    data.setValue(groupList);
                }
            }
        });

    }

}
