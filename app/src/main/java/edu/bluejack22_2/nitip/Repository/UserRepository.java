package edu.bluejack22_2.nitip.Repository;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.bluejack22_2.nitip.Database.Database;
import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.User;

public class UserRepository {
    private DatabaseReference db;
    private FirebaseAuth fAuth;
    private FirebaseFirestore dbFs;
    public UserRepository() {
        db = Database.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbFs = FirebaseFirestore.getInstance();
    }
    public void registerUser(Activity activity, User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("password", user.getUsername());
        dbFs.collection("users").add(user);

        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser users = fAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getUsername())
                                    .build();
                                System.out.println(users.getEmail());
                            if (users != null) {
                                users.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Display name is updated successfully.
                                                } else {
                                                    // An error occurred while updating the display name.
                                                }
                                            }
                                        });

                            }

                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {

                            } else {
                                // Other errors
                            }
                        }
                    }
                });
    }

    public void loginUser(String email, String password) {

    }
}
