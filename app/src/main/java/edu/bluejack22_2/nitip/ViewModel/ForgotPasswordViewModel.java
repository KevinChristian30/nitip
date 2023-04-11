package edu.bluejack22_2.nitip.ViewModel;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Repository.AuthRepository;
import edu.bluejack22_2.nitip.Service.EmailService;
import edu.bluejack22_2.nitip.Service.RandomService;
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        String emailToCheck = "example@example.com";

        Query query = usersRef.whereEqualTo("email", emailToCheck);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {

                } else {
                    response.setError(new Error("This email is doesn't exists"));
                }
            } else {
                response.setError(new Error("Something went wrong"));
            }
        });

        if (response.getError() != null) return response;

        String randomOTP = RandomService.RandomizeOTP();

        new EmailService("nitipkcrg@gmail.com", "jfoxyzupqastmoyk",
                email, "Forgot Password OTP", "Your OTP code is " + randomOTP).execute();
        authRepository.SaveOTP(email, randomOTP);

        return response;
    }


}
