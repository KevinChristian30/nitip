package edu.bluejack22_2.nitip.Service;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bluejack22_2.nitip.Model.User;

public class RegisterService {
    public static boolean isFieldEmpty(User user, String confirm) {
        return user.getEmail().trim().isEmpty() ||
                user.getPassword().trim().isEmpty() ||
                user.getUsername().trim().isEmpty() ||
                confirm.trim().isEmpty();
    }

    public static boolean isAlphanumeric(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }

    public static CompletableFuture<Boolean> checkEmailExists(String email) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> signInMethods = task.getResult().getSignInMethods();
                        if (signInMethods != null && !signInMethods.isEmpty()) {
                            // Email exists
                            future.complete(true);
                        } else {
                            // Email does not exist
                            future.complete(false);
                        }
                    } else {
                        // Error occurred
                        future.completeExceptionally(task.getException());
                    }
                });
        return future;
    }
}
