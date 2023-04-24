package edu.bluejack22_2.nitip.ViewModel;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Repository.UserRepository;

public class ChangePasswordViewModel {
    private UserRepository userRepository;
    public ChangePasswordViewModel() {
        userRepository = new UserRepository();
    }

    public interface CheckOTPCallback {
        void CheckOTP(Response response);
    }

    public void checkOTP(String otp, CheckOTPCallback callback) {
        Response response = new Response(null);

        if (otp.trim().isEmpty()) {
            response.setError(new Error("OTP must be filled"));
            callback.CheckOTP(response);
            return;
        }

        userRepository.CheckOTP(otp, new UserRepository.CheckValidOTPCallback() {
            @Override
            public void ValidOTP(Response response) {
                callback.CheckOTP(response);
            }
        });

    }

    public Response ChangePassword(String password, String confPassword) {
        Response response = new Response(null);

        if (password.trim().isEmpty() || confPassword.trim().isEmpty()) {
            response.setError(new Error("All field must be filled"));
        }
        else if (password.trim().length() < 7) {
            response.setError(new Error("Password must be more than 6 characters"));
        }
        else if (!password.equals(confPassword)) {
            response.setError(new Error("Password and Confirm Password not match"));
        }

        userRepository.ChangePassword(password);

        return response;
    }
}
