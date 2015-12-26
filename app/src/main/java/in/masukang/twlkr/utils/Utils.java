package in.masukang.twlkr.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by teresa on 12/25/2015.
 */
public class Utils {

    public static Date convertTsToDate(String s) {
        s = s.substring(4);
        DateFormat df = new SimpleDateFormat("MMM dd hh:mm:ss Z yyyy");
        try {
            return df.parse(s);
        } catch (ParseException e) {
            return new Date(0);
        }
    }
}
