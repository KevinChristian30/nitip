package edu.bluejack22_2.nitip.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.Group;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.User;

public class TitipRepository {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth fAuth;

    MutableLiveData<Titip> titipMutableLiveData;

    public TitipRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        titipMutableLiveData = new MutableLiveData<>();
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
                        titip.setId(document.getId());
                        groupList.add(titip);

                    }
                    data.setValue(groupList);
                }
            }
        });

    }

    public Response getTitipByID(String titipID) {
        Response response = new Response(null);

        Query titipsRef = firebaseFirestore.collection("titip");

        titipsRef.whereEqualTo(FieldPath.documentId(), titipID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                    String closeTime = document.getString("close_time");
                    String entrusterEmail = document.getString("entruster_email");
                    String groupCode = document.getString("group_code");
                    String groupName = document.getString("group_name");
                    String titipName = document.getString("titip_name");

                    Titip titip = new Titip(titipName, closeTime, groupCode, groupName, new ArrayList<>());
                    titip.setEntruster_email(entrusterEmail);
                    
                    titipMutableLiveData.setValue(titip);
                }

            } else {
                response.setError(new Error("Titip not found!"));
            }
        });

        response.setResponse(titipMutableLiveData);
        return response;
    }

}
