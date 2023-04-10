package edu.bluejack22_2.nitip.ViewModel;

import edu.bluejack22_2.nitip.Facade.Error;
import edu.bluejack22_2.nitip.Facade.Response;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.Service.RegisterService;
import edu.bluejack22_2.nitip.View.RegisterActivity;

public class RegisterViewModel {
    public Response RegisterUser(User user, String confirm) {

        Response response = new Response(null);

        if (RegisterService.isFieldEmpty(user, confirm)) {
            response.setError(new Error("Fields Must be Filled"));
        }
        else if (!confirm.equals(user.getPassword())) {
            response.setError(new Error("Password and Confirm Password not match"));
        }
        else if (user.getUsername().length() < 6) {
            response.setError(new Error("Username must be more than 5 characters"));
        }
        else if (RegisterService.isAlphanumeric(user.getPassword())) {
            response.setError(new Error("Password must be alphanumeric"));
        }

        if (response.getError() != null) return response;

        try {

            // Begin Tran

        } catch (Exception e) {

            response.setError(new Error("Something Went Wrong"));
            // Rollback

        }

        return response;
    }
}
