package lims.core.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class QuantumDateUtil {

    private QuantumDateUtil() {
    }

    public static String getToDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(cal.getTime());
    }

    public static Calendar getCalendar(String str) {
        int yy = Integer.parseInt(str.substring(0, 4));
        int mm = Integer.parseInt(str.substring(4, 6)) - 1;
        int dd = Integer.parseInt(str.substring(6, 8));

        Calendar cal = Calendar.getInstance();
        cal.set(yy, mm, dd);
        return cal;
    }

    public static String addDays(String str, int days) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date date = fmt.parse(str);
        date.setTime(date.getTime() + (long) days * 1000L * 60L * 60L * 24L);
        return fmt.format(date);
    }

    public static List<String> getFirstToLastDaysOfWeek(String str) throws ParseException {
        String firstDay = getFirstDayOfWeek(str, 0);

        List<String> list = new ArrayList<String>();

        for(int i=0 ; i<7 ; i++) {
            list.add(addDays(firstDay, i));
        }

        return list;

    }

    public static List<String> getDaysOfWeek(String str, int weeks) throws ParseException {

        String firstDay = addWeeks(str, -weeks);

        List<String> list = new ArrayList<String>();

        for(int i=0 ; i<=(7*weeks) ; i++) {
            list.add(addDays(firstDay, i));
        }

        return list;

    }

    public static String getFirstDayOfWeek(String str) throws ParseException {
        return getFirstDayOfWeek(str, 0);
    }


    public static String getFirstDayOfWeek(String str, int week) throws ParseException {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0) {
            conStr = str;
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        } else {
            conStr = addDays(str, week * 7);
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }

        int gap = dayOfWeek - 1;

        return addDays(conStr, -gap);
    }

    public static String getLastDayOfWeek(String str) throws ParseException {
        return getLastDayOfWeek(str, 0);
    }

    public static String getLastDayOfWeek(String str, int week) throws ParseException {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0) {
            conStr = str;
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        } else {
            conStr = addDays(str, week * 7);
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }



        int gap = 7 - dayOfWeek;

        return addDays(conStr, gap);
    }

    public static List<String> getDaysOfMonth(String str) throws ParseException {
        int endDay = getLastDayOfMonth(str);

        List<String> list = new ArrayList<String>();

        String firstDay = str.substring(0, 6) + "01";

        for(int i=0 ; i<endDay ; i++) {
            list.add(addDays(firstDay, i));
        }

        return list;

    }

    public static int getLastDayOfMonth(String str) throws ParseException {

        int endDay = getCalendar(str).getActualMaximum(Calendar.DAY_OF_MONTH);

        return endDay;
    }

    public static String getLastDayStringOfMonth(String str) throws ParseException {

        int endDay = getCalendar(str).getActualMaximum(Calendar.DAY_OF_MONTH);


        return str.substring(0, 6) + endDay;


    }

    public static String addWeeks(String str, int amount) throws ParseException {

        Date date = convertToDate(str, "yyyyMMdd");

        return convertToString(addWeeks(date, amount), "yyyyMMdd");

    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date convertToDate(String s, String pattern) throws ParseException {
        if ( StringUtils.isBlank(s) ) {
            throw new ParseException("date string to check is null", 0);
        }
        if ( StringUtils.isBlank(pattern) ) {
            throw new ParseException("pattern string to check date is null", 0);
        }
        SimpleDateFormat formatter = new SimpleDateFormat (pattern, Locale.KOREA);
        Date date = formatter.parse(s);
        return date;
    }

    public static String convertToString(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat (pattern, java.util.Locale.KOREA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static boolean isDate(String str) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            Date result = formatter.parse(str);
            String resultStr = formatter.format(result);

            if (resultStr.equalsIgnoreCase(str))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static long diffOfDate(String begin, String end) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays + 1;
    }

    public static List<String> getDaysOffsetMins(String begin, String end) throws ParseException {

        long endDay = diffOfDate(begin, end);

        List<String> list = new ArrayList<String>();



        for(int i=0 ; i<endDay ; i++) {
            list.add(addDays(begin, i));
        }

        return list;

    }

    public static String addMonths(String str, int amount) throws ParseException {

        Date date = convertToDate(str, "yyyyMMdd");

        return convertToString(addMonths(date, amount), "yyyyMMdd");

    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static String convertToFormat(String s, String pattern, String pattern1) throws ParseException {
        Date date = convertToDate(s, pattern);
        return convertToString(date, pattern1);
    }

}
