package edu.bluejack22_2.nitip.Service;

import java.util.Random;

public class RandomService {
    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private static Random rand;
    public static String RandomizeOTP() {
        rand = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
}
