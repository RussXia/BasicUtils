package org.xzy.utils.date;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by RuzzZZ on 2016/12/30.
 */
@Slf4j
public class CalendarUtil {

    /**
     * new a Calendar instance
     */
    static GregorianCalendar cldr = new GregorianCalendar();

    /**
     * the milli second of a day
     */
    public static final long DAYMILLI = 24 * 60 * 60 * 1000;

    /**
     * the milli seconds of an hour
     */
    public static final long HOURMILLI = 60 * 60 * 1000;

    /**
     * the milli seconds of a minute
     */
    public static final long MINUTEMILLI = 60 * 1000;

    /**
     * the milli seconds of a second
     */
    public static final long SECONDMILLI = 1000;

    /**
     * added time
     */
    public static final String TIMETO = " 23:59:59";
    public static final String TIMEFROM = " 00:00:00";

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;

    /**
     * set the default time zone
     */
    static {
        cldr.setTimeZone(TimeZone.getTimeZone("GMT+9:00"));
    }

    /**
     * flag before
     */
    public static final transient int BEFORE = 1;

    /**
     * flag finish
     */
    public static final transient int AFTER = 2;

    /**
     * flag equal
     */
    public static final transient int EQUAL = 3;

    /**
     * date format dd/MMM/yyyy:HH:mm:ss +0900
     */
    public static final String TIME_PATTERN_LONG = "dd/MMM/yyyy:HH:mm:ss +0900";

    /**
     * date format dd/MM/yyyy:HH:mm:ss +0900
     */
    public static final String TIME_PATTERN_LONG2 = "dd/MM/yyyy:HH:mm:ss +0900";

    /**
     * date format yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * date format YYYY-MM-DD HH24:MI:SS
     */
    public static final String DB_TIME_PATTERN = "YYYY-MM-DD HH24:MI:SS";

    /**
     * date format dd/MM/yy HH:mm:ss
     */
    public static final String TIME_PATTERN_SHORT = "dd/MM/yy HH:mm:ss";

    /**
     * date format dd/MM/yy HH24:mm
     */
    public static final String TIME_PATTERN_SHORT_1 = "yyyy/MM/dd HH:mm";

    public static final String TIME_PATTERN_SHORT_2 = "yyyyMMddHHmm";

    /**
     * date format yyyyMMddHHmmss
     */
    public static final String TIME_PATTERN_SESSION = "yyyyMMddHHmmss";

    /**
     * date format yyyyMMdd
     */
    public static final String DATE_FMT_0 = "yyyyMMdd";

    /**
     * date format yyyy/MM/dd
     */
    public static final String DATE_FMT_1 = "yyyy/MM/dd";

    /**
     * date format yyyy/MM/dd hh:mm:ss
     */
    public static final String DATE_FMT_2 = "yyyy/MM/dd hh:mm:ss";

    /**
     * date format yyyy-MM-dd
     */
    public static final String DATE_FMT_3 = "yyyy-MM-dd";

    /**
     * date format yyyy-MM-dd
     */
    public static final String DATE_FMT_4 = "yyyy年MM月dd日";

    /**
     * date format yyyy-MM-dd
     */
    public static final String DATE_FMT_5 = "dd MMM yyyy";

    /**
     * date format yyyy-MM-dd
     */
    public static final String DATE_FMT_6 = "yyyy年MM月dd日 HH点mm分";

    /**
     * date format MM/dd HH:mm:ss
     */
    public static final String DATE_FMT_7 = "MM/dd HH:mm:ss";


    /**
     * date format yyyy-MM-dd hh:mm:ss
     */
    public static final String DATE_FMT_8 = "yyyy-MM-dd HH:mm:ss";

    /**
     * date format yyyy/MM/dd HH:mm:ss
     */
    public static final String DATE_FMT_9 = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_FMT_10 = "yyyy-MM-dd HH:mm";

    /** date format */
    public static final Timestamp DATE_FROM = Timestamp.valueOf("1970-01-01 00:00:00.000000000");

    /** date to */
//    public static final Timestamp DATE_TO = Timestamp.valueOf("2015-01-01 00:00:00.000000000");

    /**
     * change string to date
     *
     * @param sDate the date string
     * @param sFmt  the date format
     * @return Date object
     */
    public static Date toDate(String sDate, String sFmt) {
        if (StringUtils.isBlank(sDate)) {
            return null;
        }
        SimpleDateFormat sdfFrom = null;
        Date dt = null;
        try {
            sdfFrom = new SimpleDateFormat(sFmt);
            dt = sdfFrom.parse(sDate);
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return null;
        } finally {
            sdfFrom = null;
        }

        return dt;
    }

    /**
     * change date to string
     *
     * @param dt a date
     * @return the format string
     */
    public static String toString(Date dt) {
        return toString(dt, DATE_FMT_0);
    }

    /**
     * change date object to string
     *
     * @param dt   date object
     * @param sFmt the date format
     * @return the formatted string
     */
    public static String toString(Date dt, String sFmt) {
        SimpleDateFormat sdfFrom = null;
        String sRet = null;

        try {
            sdfFrom = new SimpleDateFormat(sFmt);
            sRet = sdfFrom.format(dt).toString();
        } catch (Exception e) {
            log.error("日期格式化异常", e);
            return null;
        } finally {
            sdfFrom = null;
        }

        return sRet;
    }

    /**
     * get the interval days between two date
     *
     * @param dt1 date 1
     * @param dt2 date 2
     * @return the interval days
     */
    public static int getIntervalDays(Date dt1, Date dt2) {
        long intervalMilli = dt2.getTime() - dt1.getTime();

        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * get the interval hours between two date
     * @param dt1
     * @param dt2
     * @return the interval hours
     */
    public static int getIntervalHours(Date dt1, Date dt2) {
        long intervalMilli = dt2.getTime() - dt1.getTime();

        return (int) (intervalMilli / (60 * 60 * 1000));
    }

    /**
     * format the date in given pattern
     *
     * @param d       date
     * @param pattern time pattern
     * @return the formatted string
     */
    public static String formatDate(Date d, String pattern) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();

        formatter.applyPattern(pattern);
        return formatter.format(d);
    }

    public static int compareDate(Date src, Date desc) {
        if ((src == null) && (desc == null)) {
            return EQUAL;
        } else if (desc == null) {
            return BEFORE;
        } else if (src == null) {
            return AFTER;
        } else {
            long timeSrc = src.getTime();
            long timeDesc = desc.getTime();

            if (timeSrc == timeDesc) {
                return EQUAL;
            } else {
                return (timeDesc > timeSrc) ? AFTER
                        : BEFORE;
            }
        }
    }

    /**
     * (include time)
     *
     * @param first  date1
     * @param second date2
     * @return EQUAL  - if equal BEFORE - if before than date2 AFTER  - if over than date2
     */
    public static int compareTwoDate(Date first, Date second) {
        if ((first == null) && (second == null)) {
            return EQUAL;
        } else if (first == null) {
            return BEFORE;
        } else if (second == null) {
            return AFTER;
        } else if (first.before(second)) {
            return BEFORE;
        } else if (first.after(second)) {
            return AFTER;
        } else {
            return EQUAL;
        }
    }

    /**
     * @param date  the specified date
     * @param begin date1
     * @param end   date2
     * @return true  - between date1 and date2 false - not between date1 and date2
     */
    public static boolean isDateBetween(Date date, Date begin, Date end) {
        int c1 = compareTwoDate(begin, date);
        int c2 = compareTwoDate(date, end);

        return (((c1 == BEFORE) && (c2 == BEFORE)) || (c1 == EQUAL) || (c2 == EQUAL));
    }

    //use system current date as baseline date
    public static boolean isDateBetween(Date myDate, int begin, int end) {
        return isDateBetween(myDate, getCurrentDateTime(), begin, end);
    }

    public static boolean isDateBetween(Date utilDate, Date dateBaseLine,
                                        int begin, int end) {
        String pattern = TIME_PATTERN;

        String my = toString(utilDate, pattern);
        Date myDate = parseString2Date(my, pattern);

        String baseLine = toString(dateBaseLine, pattern);

        //		Date baseLineDate = parseString2Date(baseLine, pattern);
        String from = addDays(baseLine, begin);
        Date fromDate = parseString2Date(from, pattern);

        String to = addDays(baseLine, end);
        Date toDate = parseString2Date(to, pattern);

        return isDateBetween(myDate, fromDate, toDate);
    }

    /**
     * change string to Timestamp
     *
     * @param str  formatted timestamp string
     * @param sFmt string format
     * @return timestamp
     */
    public static Timestamp parseString2Timestamp(String str, String sFmt) {
        if ((str == null) || str.equals("")) {
            return null;
        }

        try {
            long time = Long.parseLong(str);

            return new Timestamp(time);
        } catch (Exception ex) {
            try {
                DateFormat df = new SimpleDateFormat(sFmt);
                Date dt = df.parse(str);

                return new Timestamp(dt.getTime());
            } catch (Exception pe) {
                try {
                    return Timestamp.valueOf(str);
                } catch (Exception e) {
                    return null;
                }
            }
        }
    }

    /**
     * parse a string into date  in a patter
     *
     * @param str  string
     * @param sFmt date pattern
     * @return date
     */
    public static Date parseString2Date(String str, String sFmt) {
        if ((str == null) || str.equals("")) {
            return null;
        }

        try {
            long time = Long.parseLong(str);

            return new Date(time);
        } catch (Exception ex) {
            try {
                DateFormat df = new SimpleDateFormat(sFmt);
                Date dt = df.parse(str);

                return new Date(dt.getTime());
            } catch (Exception pe) {
                try {
                    return null;
                } catch (Exception e) {
                    return null;
                }
            }
        }
    }

    public static String addDays(Date date, int day, String pattern) {
        return addDays(toString(date, pattern), day, pattern);
    }

    public static String addDays(Date date, int day) {
        return addDays(toString(date, TIME_PATTERN), day);
    }

    public static String addDays(String date, int day) {
        return addDays(date, day, TIME_PATTERN);
    }

    /**
     * get the time of the specified date finish given days
     *
     * @param date the specified date
     * @param day  day distance
     * @return the format string of time
     */
    public static String addDays(String date, int day, String pattern) {
        if (date == null) {
            return "";
        }

        if (date.equals("")) {
            return "";
        }

        if (day == 0) {
            return date;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Calendar calendar = dateFormat.getCalendar();

            calendar.setTime(dateFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
            return dateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * change timestamp to formatted string
     *
     * @param t    Timestamp
     * @param sFmt date format
     * @return formatted string
     */
    public static String formatTimestamp(Timestamp t, String sFmt) {
        if (t == null) {
            return "";
        }

        t.setNanos(0);

        DateFormat ft = new SimpleDateFormat(sFmt);
        String str = "";

        try {
            str = ft.format(t);
        } catch (NullPointerException e) {
            log.error("Null caught", e);
        }

        return str;
    }

    /**
     * change string to Timestamp
     *
     * @param str  formatted timestamp string
     * @param sFmt string format
     * @return timestamp
     */
    public static Timestamp parseString(String str, String sFmt) {
        if ((str == null) || str.equals("")) {
            return null;
        }
        try {
            long time = Long.parseLong(str);
            return new Timestamp(time);
        } catch (Exception ex) {
            try {
                DateFormat df = new SimpleDateFormat(sFmt);
                Date dt = df.parse(str);
                return new Timestamp(dt.getTime());
            } catch (Exception pe) {
                try {
                    return Timestamp.valueOf(str);
                } catch (Exception e) {
                    return null;
                }
            }
        }
    }

    /**
     * 把日期后的时间归0 变成(yyyy-MM-dd 00:00:00:000)
     *
     * @return Date
     */
    public static final Date zerolizedTime(Date fullDate) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(fullDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 把日期后的时间至为最大 变成(yyyy-MM-dd 23:59:59)
     *
     * @return Date
     */
    public static final Date fullTimeToMax(Date fullDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fullDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 检查修改时间
     *
     * @return Date
     */
    public static final boolean checkTime(Date createDate, Date modifiedDate) {
        if (createDate == null) {
            return true;
        }
        if (modifiedDate == null) {
            return false;
        }
        if (modifiedDate.after(zerolizedTime(new Date()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算两个日期间隔的分钟数
     *
     * @param firstDate 小者
     * @param lastDate  大者
     * @return int 默认-1
     */
    public static int getTimeIntervalMins(Date firstDate, Date lastDate) {
        if (null == firstDate || null == lastDate) {
            return -1;
        }

        long intervalMilli = lastDate.getTime() - firstDate.getTime();
        return (int) (intervalMilli / (60 * 1000));
    }

    /**
     * 计算两个日期间隔的秒数
     * @param firstDate 小者
     * @param lastDate  大者
     * @return
     * @author xiaoxia
     * @time 2016-9-21 11:03
     */
    public static int getTimeIntervalSec(Date firstDate, Date lastDate){
        if (null == firstDate || null == lastDate) {
            return -1;
        }
        long intervalMilli = lastDate.getTime() - firstDate.getTime();
        return (int) (intervalMilli /1000);
    }

    /**
     * 计算两个日期间隔的毫秒数
     *
     * @param firstDate 小者
     * @param lastDate  大者
     * @return long 默认-1
     */
    public static long getTimeIntervalMil(Date firstDate, Date lastDate) {
        if (null == firstDate || null == lastDate) {
            return 0;
        }
        long intervalMilli = lastDate.getTime() - firstDate.getTime();
        return intervalMilli > 0 ? intervalMilli : 0;
    }

    /**
     * 计算某一时间离该日结束时间相差的秒数
     * @param date
     * @return
     * @author xiaoxia
     * @time 2016-09-21 11:07
     */
    public static int getTimeIntervalSecToTodayEnd(Date date){
        String str = CalendarUtil.toString(new Date(),DATE_FMT_3);
        Date todayEnd = CalendarUtil.parseString2Date(str+" "+CalendarUtil.TIMETO,TIME_PATTERN);
        return CalendarUtil.getTimeIntervalSec(date,todayEnd);
    }

    /**
     * 增加天数
     *
     * @param date
     * @param day
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        return calendar.getTime();
    }


    /**
     * 增加秒
     * @param date
     * @param second
     * @return Date
     */
    public static Date addSecond(Date date, int second) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 增加小时(24小时制)
     * @param date
     * @param hour
     * @return Date
     */
    public static Date addHourOfDay(Date date, int hour) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static String toFullString(Date dt) {
        return toString(dt, TIME_PATTERN_SHORT_1);
    }

    /**
     * return current date
     *
     * @return current date
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * return current time
     *
     * @return current time
     */
    public static Timestamp getCurrentDateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 把计算日期时间相对现在时间 例如:5分钟前
     *
     * @return String
     */
    public static String relativeDate(Date date, String sFmt) {
        if (date == null) {
            return "未知";
        }
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR && (new Date().getDate() - date.getDate() == 1)) {
            return "昨天";
        } else {
            return toString(date, sFmt);
        }
    }

    public static String relativeDate(String strDate, String oldFmt, String newFmt) {
        return relativeDate(toDate(strDate, oldFmt), newFmt);
    }

    /**
     * UTC转local GMT.
     *
     * @param date 时间
     */
    public static Date getTimeFromUTC(Date date) {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        try {
            return dateFormatLocal.parse(dateFormatGmt.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    /**
     * 获得本周的第一天，周一的时间点
     *
     * @return
     */
    public static Date getCurrentWeekDayStartTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        //cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.setTime(cal.getTime());
        try {
            return longSdf.parse(shortSdf.format(cal.getTime()) + " 00:00:00");
        } catch (ParseException e) {
            log.error("日期格式化异常", e);
        }
        return null;
    }


    /**
     * 获取本周最后一天周日的时间
     *
     * @return
     */
    public static Date getCurrentWeekDayEndTime() {
        try {
            Date firstDayOfWeek = getCurrentWeekDayStartTime();
            Date lastDayOfWeek = CalendarUtil.addDate(firstDayOfWeek, 6);
            SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return longSdf.parse(shortSdf.format(lastDayOfWeek) + " 23:59:59");
        } catch (ParseException e) {
            log.error("日期格式化异常", e);
        }
        return null;

    }

    // 获得本月第一天0点时间
    public static Date getCurrentMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    // 获得本月最后一天24点时间
    public static Date getCurrentMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        Date startTime = CalendarUtil.toDate(CalendarUtil.toString(CalendarUtil.addDate(cal.getTime(), -1), CalendarUtil.DATE_FMT_3) + " 23:59:59", CalendarUtil.TIME_PATTERN);
        return startTime;
    }

    /**
     * 月份加减
     *
     * @param date
     * @param days
     * @return
     * @throws java.text.ParseException
     */
    public static Date addMonth(Date date, Integer days) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);

        rightNow.add(Calendar.MONTH, days);
        Date dt1 = rightNow.getTime();
        return dt1;
    }


    public static Map<String, Date> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("first", CalendarUtil.toDate(day_first, CalendarUtil.TIME_PATTERN));
        map.put("last", CalendarUtil.toDate(day_last, CalendarUtil.TIME_PATTERN));
        return map;
    }


    /**
     * 年份加减
     *
     * @param date
     * @param days
     * @return
     * @throws java.text.ParseException
     */
    public static Date addYear(Date date, Integer days) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.YEAR, days);
        Date dt1 = rightNow.getTime();
        return dt1;
    }


    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            log.error("日期格式化异常", e);
        }
        return now;
    }


    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        Date now = null;
        try {
            SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            log.error("日期格式化异常", e);
        }
        return now;
    }

    //获得本年第一天的日期
    public static Date getCurrentYearStartTime() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        Date startTime = CalendarUtil.toDate(CalendarUtil.toString(yearDay, CalendarUtil.DATE_FMT_3) + " 00:00:00", CalendarUtil.TIME_PATTERN);
        return startTime;
    }

    //获得本年最后一天的日期 *
    public static Date getCurrentYearEndTime() {
        Date date = new Date();
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
            String years = dateFormat.format(date);
            String endDate = years + "-12-31";
            now = longSdf.parse(shortSdf.format(CalendarUtil.toDate(endDate, CalendarUtil.DATE_FMT_3)) + " 23:59:59");
        } catch (ParseException e) {
            log.error("日期转换异常", e);
        }
        return now;
    }


    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    public static String formatStayTime(int hours,boolean isExport){
        StringBuilder timeStr = new StringBuilder();
        int day = hours/24;
        int hour = hours%24;
        if(day<=0){
            if(isExport){
                timeStr.append("0天");
            }
        }else{
            timeStr.append(day+"天");
        }
        timeStr.append(hour+"时");

        return timeStr.toString();
    }

    public static void main(String[] args) {
        log.info("------本周开始-----");

        log.info(CalendarUtil.toString(CalendarUtil.getCurrentWeekDayStartTime(), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentWeekDayEndTime(), CalendarUtil.TIME_PATTERN));
        log.info("------本周结束-----");

        log.info("------上周开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.addDate(CalendarUtil.getCurrentWeekDayStartTime(), -7), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.addDate(CalendarUtil.getCurrentWeekDayEndTime(), -7), CalendarUtil.TIME_PATTERN));
        log.info("------上周结束-----");


        log.info("------本月开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentMonthStartTime(), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentMonthEndTime(), CalendarUtil.TIME_PATTERN));
        log.info("------本月结束-----");

        log.info("------上月开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.getFirstday_Lastday_Month(CalendarUtil.getCurrentMonthStartTime()).get("first"), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.getFirstday_Lastday_Month(CalendarUtil.getCurrentMonthEndTime()).get("last"), CalendarUtil.TIME_PATTERN));
        log.info("------上月结束-----");

        log.info("------本季度开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentQuarterStartTime(), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentQuarterEndTime(), CalendarUtil.TIME_PATTERN));
        log.info("------本季度结束-----");

        log.info("------上季度开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.addMonth(CalendarUtil.getCurrentQuarterStartTime(), -3), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.addMonth(CalendarUtil.getCurrentQuarterEndTime(), -3), CalendarUtil.TIME_PATTERN));
        log.info("------上季度结束-----");

        log.info("------本年开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentYearStartTime(), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.getCurrentYearEndTime(), CalendarUtil.TIME_PATTERN));
        log.info("------本年结束-----");

        log.info("------上年开始-----");
        log.info(CalendarUtil.toString(CalendarUtil.addYear(CalendarUtil.getCurrentYearStartTime(), -1), CalendarUtil.TIME_PATTERN));
        log.info(CalendarUtil.toString(CalendarUtil.addYear(CalendarUtil.getCurrentYearEndTime(), -1), CalendarUtil.TIME_PATTERN));
        log.info("------上年结束-----");

        log.info("两个日期之间相隔的秒数：");
        Date date1 = CalendarUtil.parseString2Date("2016-09-21 23:59:30",TIME_PATTERN);
        log.info(CalendarUtil.getTimeIntervalSecToTodayEnd(date1)+"");

        log.info("计算倒计时：");
        Date count1 = CalendarUtil.parseString2Date("2016-11-15 10:00:00",TIME_PATTERN);
        Date count2 = CalendarUtil.parseString2Date("2016-11-17 10:00:00",TIME_PATTERN);
        log.info(CalendarUtil.countDown(count1,count2));
    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        return isTheDay(date, new Date());
    }
    /**
     * 是否是指定日期
     *
     * @param date
     * @param day
     * @return
     */
    public static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= dayBegin(day).getTime()
                && date.getTime() <= dayEnd(day).getTime();
    }
    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 返回给定date的小时数字, 24小时制
     * @param date 给定的date对象
     * @return 小时数字
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static final String DATE_FMT_11 = "MM-dd HH:mm";

    public static final String DATE_FMT_12 = "mmss";

    public static final String DATE_FMT_13 = "HH:mm";

    public static final String DATE_FMT_14 = "MM-dd";

    public static final String DATE_FMT_15 = "yyyy-MM-dd";

    public static final String DATE_FMT_16 = "MM-dd HH:mm";

    /**
     * 时间显示工具类
     * 根据时间的自然年月日显示不同的格式
     * @param date
     * @return
     */
    public static String toStringWithNatureTime(Date date){
        //时间戳距请求时间在当前自然日内：hh:mm
        if(CalendarUtil.isToday(date)){
            return CalendarUtil.toString(date,DATE_FMT_13);
        }
        //时间戳距请求时间在昨天的自然日内：昨天 hh:mm
        if(CalendarUtil.isYesterday(date)){
            return "昨天 " + CalendarUtil.toString(date,DATE_FMT_13);
        }
        //时间戳距请求时间在前天的自然日内：前天 hh:mm
        if(CalendarUtil.isDayBeforYesterDay(date)){
            return "前天 " + CalendarUtil.toString(date,DATE_FMT_13);
        }
        //时间戳距请求时间在72h外，在当前自然月内：MM-dd hh:mm
        if(CalendarUtil.isWithinThisMonth(date)){
            return CalendarUtil.toString(date,DATE_FMT_11);
        }
        //时间戳距请求时间在当前自然月外，在当前自然年内：MM-dd
        if(CalendarUtil.isWithinThisYear(date)){
            return CalendarUtil.toString(date,DATE_FMT_14);
        }
        //时间戳距请求时间在当前自然年外：yyyy-MM-dd
        return CalendarUtil.toString(date,DATE_FMT_15);
    }

    /**
     * 格式化时间为倒计时工具
     * (future - now) <= 1小时 显示 1小时
     * 1 < (future - now) <= 24 显示x小时
     * 24 < (future - now) 显示x天x小时
     * @param now 当前时间
     * @param future 未来某一时间
     * @return 倒计时文案
     */
    public static String countDown(Date now, Date future) {
        double hoursDouble =  (double)(future.getTime() - now.getTime()) / (1000 * 60 * 60);
        //向上取整
        int hours = (int)Math.ceil(hoursDouble);
        if (hours < 0) {
            return "0小时";
        }
        StringBuilder sb = new StringBuilder();
        if (hours <= 1 && hours >= 0) {
            sb.append("1小时");
        } else if (hours <= 24) {
            sb.append(hours);
            sb.append("小时");
        } else if (hours > 24) {
            int day = (int) hours / 24;
            int h = (int) hours % 24;
            sb.append(day);
            sb.append("天");
            sb.append(h);
            sb.append("小时");
        } else { // 容错
            sb.append("0小时");
        }
        return sb.toString();
    }

    /**
     * 格式化时间为倒计时工具
     * 返回距离当前时间的倒计时
     * (future - now) <= 1小时 显示 1小时
     * 1 < (future - now) <= 24 显示x小时
     * 24 < (future - now) 显示x天x小时
     * @param future 未来某一时间
     * @return 倒计时文案
     */
    public static String countDown(Date future) {
        Date now = new Date();
        return countDown(now, future);
    }

    /**
     * is yesterDay
     * @param date
     * @return
     */
    public static Boolean isYesterday(Date date){
        return isTheDay(date,CalendarUtil.addDate(new Date(),-1));
    }

    /**
     * is the day before yesterday
     * @param date
     * @return
     */
    public static Boolean isDayBeforYesterDay(Date date){
        return isTheDay(date,CalendarUtil.addDate(new Date(),-2));
    }

    /**
     * is within this month
     * @param date
     * @return
     */
    public static Boolean isWithinThisMonth(Date date){
        if(date.after(CalendarUtil.getCurrentMonthStartTime())
                && date.before(CalendarUtil.getCurrentMonthEndTime())){
            return true;
        }
        return false;
    }

    /**
     * is within this year
     * @param date
     * @return
     */
    public static Boolean isWithinThisYear(Date date){
        if(date.after(CalendarUtil.getCurrentYearStartTime())
                && date.before(CalendarUtil.getCurrentYearEndTime())){
            return true;
        }
        return false;
    }

    /**
     * 计算两个日期之间相隔的自然日天数
     * @param date1 距今较远的日期
     * @param date2 距今较近的日期
     * @return 自然天数
     */
    public static int getNaturalDays(Date date1, Date date2) {
        // 因为是自然日,取日期的开始时间计算相隔天数
        Date date1Begin = dayBegin(date1);
        Date date2Begin = dayBegin(date2);
        return getIntervalDays(date1Begin, date2Begin) + 1;
    }
}
