package edu.bluejack22_2.nitip.Service;

import java.util.Random;

public class RandomService {
    private static final String NUMBERS = "0123456789";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int OTP_LENGTH = 6;
    private static final int GROUP_CODE_LENGTH = 7;
    private static Random rand = new Random();;
    public static String RandomizeOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(NUMBERS.charAt(rand.nextInt(NUMBERS.length())));
        }
        return otp.toString();
    }

    public static String RandomizeGroupCode() {
        StringBuilder groupCode = new StringBuilder();
        for (int i = 0; i < GROUP_CODE_LENGTH; i++) {
            groupCode.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS.length())));
        }
        return groupCode.toString();
    }
}
