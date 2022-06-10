package com.example.myapp.utils;

import android.util.Log;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class InputValidator {

    /**
     * validate Email
     * @param input
     * @return Boolean
     */
    static public Boolean validateEmail(String input) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "(gmail)(\\.(com))$";
        return Pattern.compile(regexPattern).matcher(input).matches();
    }

    /**
     * validate Input time before milestone
     * @param _input
     * @param milestone
     * @return Boolean
     */
    static public Boolean timeBefore(String milestone, String _input) throws ParseException {
        Timestamp input = stringToTimestamp(_input);
        if (input.before(stringToTimestamp(milestone)))
            return true;
        else return false;
    }

    /**
     * validate Input time after milestone
     * @param _input
     * @param milestone
     * @return Boolean
     */
    static public Boolean timeAfter(String milestone, String _input) throws ParseException {
        Timestamp input = stringToTimestamp(_input);
        if (input.after(stringToTimestamp(milestone)))
            return true;
        else return false;
    }

    /**
     * validate Input time between start-end
     * @param _input
     * @param start
     * @param end
     * @return Boolean
     */
    static public Boolean timeBetween(String start, String end, String _input) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date acceptStart = timeFormat.parse(start);
        Date acceptEnd = timeFormat.parse(end);
        Timestamp input = new Timestamp(timeFormat.parse(_input).getTime());

        if(input.before(acceptEnd) && input.after(acceptStart))
            return true;
        else
            return false;
    }

    /**
     * validate Input time is Weekdays
     * @param _input
     * @return Boolean
     * @throws ParseException
     */
    static public Boolean timeWeekdays(String _input) throws ParseException {
        Timestamp input = stringToTimestamp(_input);
        String day = (new SimpleDateFormat("EEEE")).format(input.getTime());
        if(day.equals("Chủ Nhật"))
            return false;
        else
            return true;
    }

    /**
     * validate Input password
     * @param input
     * @return Boolean
     */
    static public Boolean validatePassword(String input) {
        String regexPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        return Pattern.compile(regexPattern).matcher(input).matches();
    }

    /**
     * validate Input string length
     * @param min
     * @param max
     * @param input
     * @return Boolean
     */
    static public Boolean validateExtendInput(int min, int max, String input) {
        if(input.length() < min || input.length() > max)
            return false;
        else return true;
    }

    /**
     * validate Input string no space
     * @param input
     * @return Boolean
     */
    static public Boolean noWhiteSpace(String input){
        return input.matches("^\\S+$");
    }

    /**
     * Turn String to Timestamp with format dd-MM-yyyy HH:mm:ss
     * @param input
     * @return Timestamp
     * @throws ParseException
     */
    public static Timestamp stringToTimestamp(String input) throws ParseException {
        String ISO_DATE_FORMAT_ZERO_OFFSET = "dd-MM-yyyy HH:mm:ss";
        String UTC_TIMEZONE_NAME = "UTC";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_DATE_FORMAT_ZERO_OFFSET);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE_NAME));
        Timestamp t = new Timestamp(simpleDateFormat.parse(input).getTime());
        return t;
    }

    /**
     * Turn String to Timestamp with format HH:mm
     * @param input
     * @return Timestamp
     * @throws ParseException
     */
    public static Timestamp timeStringToTimestamp(String input) throws ParseException {
        String ISO_DATE_FORMAT_ZERO_OFFSET = "HH:mm";
        String UTC_TIMEZONE_NAME = "UTC";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_DATE_FORMAT_ZERO_OFFSET);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE_NAME));
        Timestamp t = new Timestamp(simpleDateFormat.parse(input).getTime());
        return t;
    }
}
