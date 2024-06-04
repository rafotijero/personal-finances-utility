package org.tijfuen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date parseDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
