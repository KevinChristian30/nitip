package edu.bluejack22_2.nitip.Service;

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
}
