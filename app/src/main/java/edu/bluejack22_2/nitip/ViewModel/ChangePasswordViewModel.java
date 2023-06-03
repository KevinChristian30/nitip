package edu.bluejack22_2.nitip.ViewModel;

import android.content.Context;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Repository.UserRepository;

public class ChangePasswordViewModel {
    private UserRepository userRepository;
    public ChangePasswordViewModel() {
        userRepository = new UserRepository();
    }

    public interface CheckOTPCallback {
        void CheckOTP(Response response);
    }

    public void checkOTP(Context context, String otp, CheckOTPCallback callback) {
        Response response = new Response(null);

        if (otp.trim().isEmpty()) {
            response.setError(new Error(context.getResources().getString(R.string.otp_must_filled)));
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

    public Response ChangePassword(Context context, String password, String confPassword) {
        Response response = new Response(null);

        if (password.trim().isEmpty() || confPassword.trim().isEmpty()) {
            response.setError(new Error(context.getResources().getString(R.string.field_must_filled)));
        }
        else if (password.trim().length() < 7) {
            response.setError(new Error(context.getResources().getString(R.string.password_6_char)));
        }
        else if (!password.equals(confPassword)) {
            response.setError(new Error(context.getResources().getString(R.string.password_conf_not_match)));
        }

        userRepository.ChangePassword(password);

        return response;
    }
}
