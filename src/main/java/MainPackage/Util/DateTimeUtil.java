package MainPackage.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-DD");

    public static Date parseDatetimeFromString(String strDateTime) {
        try {
            return simpleDateFormat.parse(strDateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }
}
