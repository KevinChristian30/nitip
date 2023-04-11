package edu.bluejack22_2.nitip.Repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthRepository {
    private FirebaseFirestore db;

    public AuthRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void SaveOTP(String email, String OTP) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("otp", OTP);
        data.put("valid_time", LocalDateTime.now().plusMinutes(15));
        db.collection("otps").add(data);
    }
}
