package in.masukang.twlkr.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by teresa on 12/25/2015.
 */
public class Utils {

    /**
     * Converts String timestamp to Date
     * @param s Fabric's Tweet object timestamp format (ex: Sun Dec 27 07:08:09 +0000 2015)
     * @return s in Java's Date format. If not match, return 1 Jan 1970
     */
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
