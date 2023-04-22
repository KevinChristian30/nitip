package edu.bluejack22_2.nitip.Repository;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import edu.bluejack22_2.nitip.Database.Database;
import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.EmailService;
import edu.bluejack22_2.nitip.View.LoginActivity;

public class UserRepository {
    private DatabaseReference db;
    private FirebaseAuth fAuth;
    private FirebaseFirestore dbFs;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private MutableLiveData<User> userMutableLiveData;
    public UserRepository() {
        db = Database.getInstance();
        fAuth = FirebaseAuth.getInstance();
        dbFs = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        userMutableLiveData = new MutableLiveData<>();
    }

    private ByteArrayOutputStream bitmapToByteArrayOutputStream(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream;
    }

    private void AddDataToFirestore(Activity activity, User user) {
        // Get the drawable from resources
        Drawable drawable = activity.getDrawable(R.drawable.baseline_account_circle_24);

        // Convert the drawable to a bitmap
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        // Convert the Bitmap to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = bitmapToByteArrayOutputStream(bitmap);

        // Generate a unique name for the image
        String imageFileName = user.getEmail();

        // Get a reference to the Firebase Storage location
        StorageReference imageRef = storageReference.child("profile_pictures/" + imageFileName);

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(outputStream.toByteArray());
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();

                // Prepare the data to be saved to Firestore
                Map<String, Object> data = new HashMap<>();
                data.put("email", user.getEmail());
                data.put("username", user.getUsername());
                data.put("profile_picture", imageUrl);

                // Save the data to Firestore
                dbFs.collection("users").add(data)
                        .addOnSuccessListener(documentReference -> {

                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(activity, "Failed to register user", Toast.LENGTH_SHORT).show();
                        });
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(activity, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
        });
    }

    public void registerUser(Activity activity, User user) {
        AddDataToFirestore(activity, user);
        fAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

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

    public void registerGoogleUser(Activity activity, User user) {

        EmailService.isEmailExists(user.getEmail(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && querySnapshot.isEmpty()) {
                        System.out.println(user.getUsername());
                        AddDataToFirestore(activity, user);
                    } else {

                    }
                } else {
                    System.out.println("fail");
                }

            }
        });
    }

    public Response getUser(String userEmail) {
        Response response = new Response(null);
        dbFs.collection("users").whereEqualTo("email", userEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    String username = document.getString("username");
                    String email = document.getString("email");
                    String profile = document.getString("profile_picture");
                    User user = new User(username, email, "", profile);
                    userMutableLiveData.setValue(user);
                }
            } else {
                response.setError(new Error("User data not found!"));
            }
        });
        response.setResponse(userMutableLiveData);
        return response;
    }


    public interface OnProfilePictureUpdatedListener {
        void onSuccess(User updatedUser);
        void onFailure(Exception e);
    }

    public void ChangeProfilePicture(Uri imageURI, OnProfilePictureUpdatedListener listener) {
        String imageName = fAuth.getCurrentUser().getEmail();
        StorageReference path = storageReference.child("profile_pictures/" + imageName);

        UploadTask uploadTask = path.putFile(imageURI);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
           path.getDownloadUrl().addOnCompleteListener(url -> {
               dbFs.collection("users").
                       whereEqualTo("email", fAuth.getCurrentUser().getEmail()).
                       get().addOnCompleteListener(e -> {
                           if (e.isSuccessful()) {
                                DocumentSnapshot currUser = e.getResult().getDocuments().get(0);
                                currUser.getReference().update("profile_picture", url.getResult())
                                        .addOnSuccessListener(suc -> {
                                            User updatedUser = new User(currUser.getString("username"),
                                                    currUser.getString("email"),
                                                    currUser.getString("password"),
                                                    url.getResult().toString());
                                            listener.onSuccess(updatedUser);
                                        })
                                        .addOnFailureListener(fail -> {
                                            listener.onFailure(new Exception("Something went wrong"));
                                        });


                           }
                       });

           });
        });
    }

    public interface CheckValidOTPCallback {
        public void ValidOTP(Response response);
    }

    public void CheckOTP(String otp, CheckValidOTPCallback callback) {
        Response response = new Response(null);
        dbFs.collection("otps")
                .whereEqualTo("otp", otp)
                .whereEqualTo("email", fAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String expiryTimeString = documentSnapshot.getString("valid_time");

                            if (expiryTimeString != null) {
                                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                                LocalDateTime expiryTime = LocalDateTime.parse(expiryTimeString, formatter);
                                LocalDateTime currentTime = LocalDateTime.now();

                                if (expiryTime.isAfter(currentTime)) {

                                } else {
                                    response.setError(new Error("The OTP is expired"));
                                }
                            }
                        }
                        else {
                            response.setError(new Error("Invalid OTP"));
                        }
                    }
                    else {
                        response.setError(new Error("Something went wrong"));
                    }
                    callback.ValidOTP(response);
                });
    }

    public void ChangePassword(String password) {
        FirebaseUser currUser = fAuth.getCurrentUser();

        if (currUser != null) {
            currUser.updatePassword(password);
        }
    }

}
