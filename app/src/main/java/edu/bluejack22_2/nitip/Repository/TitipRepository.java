package edu.bluejack22_2.nitip.Repository;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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
        titipData.put("entruster_email", titip.getEntrusterEmail());

        firebaseFirestore.collection("titip").add(titipData);
    }

}
