package edu.bluejack22_2.nitip.ViewModel;

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

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Repository.UserRepository;

public class LoginViewModel {
    MutableLiveData<FirebaseUser> userLiveData;

    private MutableLiveData<FirebaseUser> currentUserLiveData;
    private FirebaseUser currentUserData;
    private FirebaseAuth firebaseAuth;

    public LoginViewModel(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        currentUserLiveData = new MutableLiveData<>();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUserData = firebaseAuth.getCurrentUser();
                            currentUserLiveData.setValue(currentUserData);
                        } else {

                        }
                    }
                });
    }

    public void googleLogout() {
        firebaseAuth.signOut();
    }

    public Response loginWithEmailandPassword(String email, String password) {
        Response response = new Response(null);

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            response.setError(new Error("All field must be filled"));
        }

        return response;
    }
}
