package edu.bluejack22_2.nitip.Repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.Group;
import edu.bluejack22_2.nitip.Model.GroupRow;
import edu.bluejack22_2.nitip.Model.Titip;
import edu.bluejack22_2.nitip.Model.TitipDetail;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.Service.TimeService;
import edu.bluejack22_2.nitip.View.NitipDetailActivity;

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

                    ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) document.get("titip_detail");
                    ArrayList<TitipDetail> titipDetails = new ArrayList<>();
                    for (HashMap<String, Object> map : data) {
                        HashMap<String, Object> object = (HashMap<String, Object>) map.get("user");
                        titipDetails.add(
                            new TitipDetail(
                                new User((String) object.get("username"),
                                        (String) object.get("email"),
                                        (String) object.get("profile")),
                                        (String) map.get("detail")));
                    }

                    Titip titip = new Titip(titipName, closeTime, groupCode, groupName, titipDetails);
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

    public void addNewTitipDetail(String titipID, TitipDetail titipDetail) {
        DocumentReference documentRef = firebaseFirestore.collection("titip").document(titipID);

        documentRef.get().addOnSuccessListener(documentSnapshot -> {
            List<Map<String, Object>> array = (List<Map<String, Object>>) documentSnapshot.get("titip_detail");

            if (array != null) {
                Map<String, Object> newTitipDetailUser = new HashMap<>();
                newTitipDetailUser.put("username", titipDetail.getUser().getUsername());
                newTitipDetailUser.put("email", titipDetail.getUser().getEmail());
                newTitipDetailUser.put("profile", titipDetail.getUser().getProfile());

                Map<String, Object> newTitipDetail = new HashMap<>();
                newTitipDetail.put("user", newTitipDetailUser);
                newTitipDetail.put("detail", titipDetail.getDetail());

                array.add(newTitipDetail);

                documentRef.update("titip_detail", array);
            }
        });
    }

    public CompletableFuture<Response> getLastTitip(String groupCode) {
        CompletableFuture<Response> futureResponse = new CompletableFuture<>();
        Response response = new Response(null);

        Query titipsRef = firebaseFirestore.collection("titip").whereEqualTo("group_code", groupCode);
        titipsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Titip lastTitip = new Titip("", "2500-05-20 15:25", "", "", new ArrayList<>());
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Titip titip = document.toObject(Titip.class);
                        titip.setId(document.getId());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime closeTime = LocalDateTime.parse(titip.getClose_time(), formatter);

                        if (closeTime.isAfter(TimeService.getCurrentTimeWithFormat()) && (lastTitip == null || closeTime.isBefore(LocalDateTime.parse(lastTitip.getClose_time(), formatter)))) {
                            lastTitip = titip;
                        }

                    }
                    response.setResponse(lastTitip.getTitip_name());

                    futureResponse.complete(response);

                }
                else {
                    futureResponse.completeExceptionally(task.getException());
                }
            }
        });
        return futureResponse;
    }

    public interface Listener {
        void onSuccess(ArrayList<TitipDetail> titipDetails);
        void onFailure();
    }

    public void updateTitipDetail(String titipID, String email, String newDetail, Listener listener) {
        Query titipsRef = firebaseFirestore.collection("titip");

        ArrayList<TitipDetail> titipDetails = new ArrayList<>();
        titipsRef.whereEqualTo(FieldPath.documentId(), titipID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                    ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) document.get("titip_detail");
                    for (HashMap<String, Object> map : data) {
                        HashMap<String, Object> object = (HashMap<String, Object>) map.get("user");

                        if (((String) object.get("email")).equals(email)) {
                            titipDetails.add(
                                new TitipDetail(
                                    new User((String) object.get("username"),
                                        (String) object.get("email"),
                                        (String) object.get("profile")),
                                        newDetail));
                        } else {
                            titipDetails.add(
                                new TitipDetail(
                                    new User((String) object.get("username"),
                                        (String) object.get("email"),
                                        (String) object.get("profile")),
                                        (String) map.get("detail")));
                        }

                    }
                }
            }
        });

        titipsRef.whereEqualTo(FieldPath.documentId(), titipID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    document.getReference().update("titip_detail", titipDetails);
                }

                listener.onSuccess(titipDetails);
            }
        });

    }

    public void deleteTitipDetail(String titipID, String email) {

    }
}
