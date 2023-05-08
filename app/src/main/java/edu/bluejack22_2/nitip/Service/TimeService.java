package edu.bluejack22_2.nitip.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeService {
    public static boolean isValidDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(dateString);
            return date != null && date.after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}