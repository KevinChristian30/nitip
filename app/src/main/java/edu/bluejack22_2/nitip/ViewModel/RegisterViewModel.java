package edu.bluejack22_2.nitip.ViewModel;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.Repository.UserRepository;
import edu.bluejack22_2.nitip.Service.RegisterService;
import edu.bluejack22_2.nitip.View.RegisterActivity;

public class RegisterViewModel extends ViewModel {
    private UserRepository userRepository;
    public RegisterViewModel() {
        userRepository = new UserRepository();
    }
    public Response RegisterUser(Activity activity, User user, String confirm) {

        Response response = new Response(null);

        if (RegisterService.isFieldEmpty(user, confirm)) {
            response.setError(new Error("All Fields Must be Filled"));
        }
        else if (user.getUsername().length() < 6) {
            response.setError(new Error("Username must be more than 5 characters"));
        }
        else if (!RegisterService.isValidEmail(user.getEmail())) {
            response.setError(new Error("Invalid Email"));
        }
        else if (user.getPassword().trim().length() < 7) {
            response.setError(new Error("Password must be more than 6 charaacters"));
        }
        else if (!RegisterService.isAlphanumeric(user.getPassword())) {
            response.setError(new Error("Password must be alphanumeric"));
        }
        else if (!confirm.equals(user.getPassword())) {
            response.setError(new Error("Password and Confirm Password not match"));
        }


        if (response.getError() != null) return response;

        try {

            userRepository.registerUser(activity, user);

        } catch (Exception e) {

            response.setError(new Error("Something Went Wrong"));

        }


        return response;
    }
}
