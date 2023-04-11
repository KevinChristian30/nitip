package edu.bluejack22_2.nitip.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Repository.AuthRepository;
import edu.bluejack22_2.nitip.Service.RegisterService;

public class ForgotPasswordViewModel {
    AuthRepository authRepository;
    FirebaseAuth firebaseAuth;
    public ForgotPasswordViewModel() {
        authRepository = new AuthRepository();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Response SendOTP(String email) {
        Response response = new Response(null);

        if (!RegisterService.isValidEmail(email)) {
            response.setError(new Error("Invalid email"));
        }

        if (response.getError().getMessage().isEmpty()) return response;

//        SendEmail

        return response;
    }
}
