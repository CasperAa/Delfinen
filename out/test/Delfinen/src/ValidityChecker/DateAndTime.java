package ValidityChecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Casper

public class DateAndTime {

    public static boolean dateValidation(String date) {
        boolean status = false;
        if (checkDate(date)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                status = true;
            } catch (Exception e) {
                status = false;
            }
        }
        return status;
    }

    static boolean checkDate(String date) {
        String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
        boolean flag = false;
        if (date.matches(pattern)) {
            flag = true;
        }
        return flag;
    }


    public static boolean validateTime(final String time) {
        Pattern pattern;
        Matcher matcher;
        final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        pattern = Pattern.compile(TIME24HOURS_PATTERN);

        matcher = pattern.matcher(time);
        return matcher.matches();
    }
}