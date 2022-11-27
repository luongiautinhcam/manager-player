package vn.edu.stu.quanlycauthu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
    static SimpleDateFormat sdfDateTime =
            new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
    static  SimpleDateFormat sdfDate =
            new SimpleDateFormat("dd/MM/yyyy");
    static  SimpleDateFormat sdfTime =
            new SimpleDateFormat("hh:mm aa");

    private static final String Pattern = "dd/MM/yyyy";

    public static Date toDate(String st) throws ParseException {
        SimpleDateFormat sdf =  new SimpleDateFormat(Pattern);

        return sdf.parse(st);
    }

    public static String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Pattern);

        return sdf.format(date);
    }

    public static  String formatDateTime (Date date) {
        return sdfDateTime.format(date);
    }

    public static  String formatDate (Date date) {
        return sdfDate.format(date);
    }

    public static  String formatTime (Date date) {
        return sdfTime.format(date);
    }
}
