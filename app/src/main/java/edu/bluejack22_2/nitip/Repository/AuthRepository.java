package edu.bluejack22_2.nitip.Repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AuthRepository {
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public AuthRepository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void SaveOTP(String email, String OTP) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("otp", OTP);

        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15); // 10 minutes from now
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String expiryTimeString = expiryTime.format(formatter);

        data.put("valid_time", expiryTimeString);
        db.collection("otps").add(data);
    }

    public void SendResetPasswordRequest(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}
