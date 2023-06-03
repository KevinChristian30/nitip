package edu.bluejack22_2.nitip.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Repository.UserRepository;
import edu.bluejack22_2.nitip.View.HomeActivity;

public class LoginViewModel {

    private MutableLiveData<FirebaseUser> currentUserLiveData = new MutableLiveData<>();
    private FirebaseUser currentUserData;
    private FirebaseAuth firebaseAuth;
    private UserRepository userRepository;

    public LoginViewModel(){
        firebaseAuth = FirebaseAuth.getInstance();
        userRepository = new UserRepository();
    }

    public void firebaseAuthWithGoogle(Activity activity, GoogleSignInAccount acct, Context context) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        currentUserLiveData = new MutableLiveData<>();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUserData = firebaseAuth.getCurrentUser();
                            currentUserLiveData.setValue(currentUserData);
                            userRepository.registerGoogleUser(activity, new User(currentUserData.getDisplayName(), currentUserData.getEmail(), ""));

                            ActivityChanger.changeActivity(context, HomeActivity.class);
                        } else {

                        }
                    }
                });
    }

    public void googleLogout() {
        firebaseAuth.signOut();
    }

    public interface LoginWithEmailAndPasswordCallback {
        void onLoginResponse(Response response);
    }

    public void loginWithEmailandPassword(Context context, String email, String password, LoginWithEmailAndPasswordCallback callback) {
        Response response = new Response(null);

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            response.setError(new Error(context.getResources().getString(R.string.field_must_filled)));
            callback.onLoginResponse(response);
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        currentUserData = firebaseAuth.getCurrentUser();
                        currentUserLiveData.setValue(currentUserData);
                        // Do something with the user object, such as redirect to a new activity
                    } else {
                        // If sign in fails, display a message to the user.
                        response.setError(new Error(context.getResources().getString(R.string.invalid_credential)));
                    }
                    callback.onLoginResponse(response);
                });


    }
}
