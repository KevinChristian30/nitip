package edu.bluejack22_2.nitip.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Repository.AuthRepository;
import edu.bluejack22_2.nitip.Service.EmailService;
import edu.bluejack22_2.nitip.Service.RegisterService;

public class ForgotPasswordViewModel {
    AuthRepository authRepository;
    FirebaseAuth firebaseAuth;
    public ForgotPasswordViewModel() {
        authRepository = new AuthRepository();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public interface SendResetPasswordRequestCallBack {
        void onSendOtpResponse(Response response);
    }

    public void SendResetPasswordRequest(Context context, String email, SendResetPasswordRequestCallBack callBack) {
        Response response = new Response(null);

        if (email.trim().isEmpty()) {
            response.setError(new Error(context.getResources().getString(R.string.email_must_filled)));
        }
        else if (!RegisterService.isValidEmail(email)) {
            response.setError(new Error(context.getResources().getString(R.string.invalid_email)));
        }

        if (response.getError() != null)
            callBack.onSendOtpResponse(response);

        EmailService.isEmailExists(email, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        authRepository.SendResetPasswordRequest(email);
                    } else {
                        response.setError(new Error(context.getResources().getString(R.string.email_doesnt_exist)));
                    }
                } else {
                    response.setError(new Error(context.getResources().getString(R.string.something_went_wrong)));
                }
                callBack.onSendOtpResponse(response);
            }
        });

    }


}
