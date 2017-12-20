package com.hdh.common.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期、时间 处理类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:36
 */
public class DateUtil {

    public static final String HH_MM_12 = "KK:mm";
    public static final String HH_MM_24 = "HH:mm";
    public static final String MM_DD = "MM-dd";
    public static final String YYYY_MM_DD1 = "yyyy/MM/dd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy年MM月";
    public static final String YYMMDD = "yy/MM/dd";
    public static final String YYYYMMDD = "yyyy.MM.dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YY_MM_DD_HH_MM = "yy-MM-dd HH:mm";
    public static final String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String MMDD_HH_MM = "MM/dd HH:mm";
    public static final String YY_MM_DD = "yy-MM-dd";
    /**
     * 发布日期 主要防止手机时间不准的客户 例如当前 手机时间是1970年的
     */
    public static final String ISSUE_DATA = "2015-09-15";
    /**
     * HTTP协议日期格式规范
     */
    private static final String RFC_1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
    /**
     * 地区, 区域
     */
    private static final Locale LOCALE = Locale.US;
    /**
     * GMT时区
     */
    private static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");
    // 短日期格式
    public static String DATE_FORMAT = "yyyy-MM-dd";
    private static final long issueData = convert2long(ISSUE_DATA, DATE_FORMAT);
    // 长日期格式
    public static String TIME_FORMAT_S = "yyyy-MM-dd HH:mm";
    private static Calendar sCalendar;

    /**
     * 获取当前日期时间(HTTP协议格式规范)
     *
     * @return
     */
    public static String getCurrentHttpDatetime() {
        DateFormat sdf = new SimpleDateFormat(RFC_1123_PATTERN, LOCALE);
        sdf.setTimeZone(GMT_ZONE);
        return sdf.format(new Date());
    }

    /**
     * 对比时间
     *
     * @param milliseconds
     * @return
     */
    public static TimeName compareTime(long milliseconds) {
        return compareTime(milliseconds, System.currentTimeMillis());
    }

    /**
     * 对比时间
     *
     * @param milliseconds
     * @param current
     * @return
     */
    public static TimeName compareTime(long milliseconds, long current) {
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(current);

        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds);

        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);

        if (y == 0) {
            return compareMonth(calendarUser, calendarCurrent);
        } else if (y == -1) {
            // 跨年份，
            int m = calendarUser.get(Calendar.MONTH)
                    - (calendarCurrent.get(Calendar.MONTH) + 12);
            if (m == -1) {
                // 上个月是12月时
                int d = calendarUser.get(Calendar.DAY_OF_MONTH)
                        - (calendarCurrent.get(Calendar.DAY_OF_MONTH) + 31);
                if (d >= -3) {
                    // 前两天，则算名称按昨天前天
                    return compareDay(d);
                } else {
                    return TimeName.last_month;
                }
            } else {
                return TimeName.last_year;
            }
        } else if (y <= -2) {
            return TimeName.last_year_more;
        } else if (y == 1) {
            // 跨年份
            int m = calendarUser.get(Calendar.MONTH) + 12
                    - calendarCurrent.get(Calendar.MONTH);
            if (m == 1) {
                // 下个月是1月
                int d = calendarUser.get(Calendar.DAY_OF_MONTH) + 31
                        - calendarCurrent.get(Calendar.DAY_OF_MONTH);
                if (d <= 3) {
                    // 前两天，则算名称按昨天前天
                    return compareDay(d);
                } else {
                    return TimeName.next_month;
                }
            } else {
                return TimeName.next_year;
            }
        } else {
            return TimeName.next_year_more;
        }
    }

    private static TimeName compareMonth(Calendar calendarUser, Calendar calendarCurrent) {
        int m = calendarUser.get(Calendar.MONTH) - calendarCurrent.get(Calendar.MONTH);
        int d = calendarUser.get(Calendar.DAY_OF_YEAR)
                - calendarCurrent.get(Calendar.DAY_OF_YEAR);
        if (m == 0) {
            // 当月
            return compareDay(d);
        } else if (m == -1) {
            // 上个月
            if (d >= -3) {
                // 前两天，则算名称按昨天前天
                return compareDay(d);
            } else {
                return TimeName.last_month;
            }
        } else if (m <= -2) {
            return TimeName.last_month_more;
        } else if (m == 1) {
            // 下个月
            if (d <= 3) {
                // 后两天，则算名称按明天后天
                return compareDay(d);
            } else {
                return TimeName.next_month;
            }
        } else {
            // 下下个月
            return TimeName.next_month_more;
        }
    }

    private static TimeName compareDay(int d) {
        if (d == 0) {
            return TimeName.today;
        } else if (d == -1) {
            return TimeName.yesterday;
        } else if (d == -2) {
            return TimeName.the_day_before_yesterday;
        } else if (d < -2) {
            return TimeName.the_day_before_yesterday_more;
        } else if (d == 1) {
            return TimeName.tomorrow;
        } else if (d == 2) {
            return TimeName.the_day_after_tomorrow;
        } else {
            return TimeName.the_day_after_tomorrow_more;
        }
    }

    private static String getTimeQuantumName(TimeQuantum timeQuantum) {
        String name = "";
        if (timeQuantum == TimeQuantum.wee_hours) {
            name = "凌晨";
        } else if (timeQuantum == TimeQuantum.forenoon) {
            name = "上午";
        } else if (timeQuantum == TimeQuantum.nooning) {
            name = "中午";
        } else if (timeQuantum == TimeQuantum.afternoon) {
            name = "下午";
        } else {
            name = "晚上";
        }
        return name;
    }

    public static TimeQuantum getTimeQuantum(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds);
        int h = calendarUser.get(Calendar.HOUR_OF_DAY);
        if (h >= 0 && h < 6) {// 凌晨:24点-6点
            return TimeQuantum.wee_hours;
        } else if (h >= 6 && h < 12) {// 上午:6点-12点
            return TimeQuantum.forenoon;
        } else if (h >= 12 && h < 14) {// 中午:12点-14点
            return TimeQuantum.nooning;
        } else if (h >= 14 && h < 18) {// 下午:14点-18点
            return TimeQuantum.afternoon;
        } else {// 晚上:18点-24点
            return TimeQuantum.night;
        }
    }

    /**
     * 格式化日期时间
     *
     * @param milliseconds
     * @param format
     * @return
     */
    public static String format(long milliseconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(milliseconds));
    }

    public static String getYearMonth(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM, Locale.getDefault());
        return sdf.format(new Date(milliseconds));
    }

    /**
     * 会话列表时间格式化
     *
     * @param milliseconds
     * @return
     */
    public static String dateFormatChatListImpl(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds == 0 ? System.currentTimeMillis()
                : milliseconds);
        Calendar calendarCurrent = Calendar.getInstance();
        long current = System.currentTimeMillis();
        calendarCurrent.setTimeInMillis(current);
        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);
        if (y == 0) {
            int m = calendarUser.get(Calendar.MONTH)
                    - calendarCurrent.get(Calendar.MONTH);
            int d = calendarUser.get(Calendar.DAY_OF_YEAR)
                    - calendarCurrent.get(Calendar.DAY_OF_YEAR);
            if (m == 0) {
                if (d == 0) {
                    // 当天
                    return format(milliseconds, HH_MM_24);
                } else if (d == -1) {
                    // 超过当天一天少于两天
                    return "昨天";
                } else if (d <= -2 && d > -7) {
                    // 超过两天少于七天
                    return getWeek(milliseconds);
                } else {
                    // 超过7天少于一年
                    return format(milliseconds, MM_DD);
                }
            } else {
                // 超过7天少于一年
                return format(milliseconds, MM_DD);
            }
        } else if (y == -1) {
            int m = calendarUser.get(Calendar.MONTH)
                    - (calendarCurrent.get(Calendar.MONTH) + 12);
            if (m == -1) {
                int d = calendarUser.get(Calendar.DAY_OF_MONTH)
                        - (calendarCurrent.get(Calendar.DAY_OF_MONTH) + 31);
                if (d == -1) {
                    // 超过当天一天少于两天
                    return "昨天";
                } else if (d <= -2 && d > -7) {
                    // 超过两天少于七天
                    return getWeek(milliseconds);
                } else {
                    // 超过7天少于一年
                    return format(milliseconds, MM_DD);
                }
            }
            return format(milliseconds, MM_DD);
        }
        return format(milliseconds, YYYY_MM_DD);
    }

    public static String getWeek(int num){
        switch (num){
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
            default:
                return "";
        }
    }
    public static String getWeek(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds);
        int mWay = calendarUser.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (mWay) {
            case Calendar.SUNDAY:
                week = "周日";
                break;
            case Calendar.MONDAY:
                week = "周一";
                break;
            case Calendar.TUESDAY:
                week = "周二";
                break;
            case Calendar.WEDNESDAY:
                week = "周三";
                break;
            case Calendar.THURSDAY:
                week = "周四";
                break;
            case Calendar.FRIDAY:
                week = "周五";
                break;
            case Calendar.SATURDAY:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * 评论时间格式化
     *
     * @param milliseconds
     * @return
     */
    public static String dateFormatCommentImpl(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds == 0 ? System.currentTimeMillis()
                : milliseconds);
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(System.currentTimeMillis());
        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);
        if (y == 0) {
            int m = calendarUser.get(Calendar.MONTH)
                    - calendarCurrent.get(Calendar.MONTH);
            int d = calendarUser.get(Calendar.DAY_OF_YEAR)
                    - calendarCurrent.get(Calendar.DAY_OF_YEAR);
            if (m == 0) {
                if (d == 0) {
                    // 当天
                    return format(milliseconds, HH_MM_24);
                }
            }
            return format(milliseconds, MM_DD_HH_MM);
        } else if (y <= -1) {
            return format(milliseconds, YY_MM_DD);
        }
        return format(milliseconds, YY_MM_DD);
    }

    /**
     * 评论时间格式化
     *
     * @param milliseconds
     * @return
     */
    public static String dateFormatTieziImpl(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds == 0 ? System.currentTimeMillis()
                : milliseconds);
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(System.currentTimeMillis());
        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);
        if (y == 0) {
            int m = calendarUser.get(Calendar.MONTH)
                    - calendarCurrent.get(Calendar.MONTH);
            int d = calendarUser.get(Calendar.DAY_OF_YEAR)
                    - calendarCurrent.get(Calendar.DAY_OF_YEAR);
            if (m == 0) {
                if (d == 0) {
                    // 当天
                    return format(milliseconds, HH_MM_24);
                }
            }
            return format(milliseconds, MMDD_HH_MM);
        } else if (y <= -1) {
            return format(milliseconds, YYMMDD);
        }
        return format(milliseconds, YYMMDD);
    }

    public static String dateFormatNotifyImpl(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds == 0 ? System.currentTimeMillis()
                : milliseconds);
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(System.currentTimeMillis());
        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);
        if (y == 0) {
            int m = calendarUser.get(Calendar.MONTH)
                    - calendarCurrent.get(Calendar.MONTH);
            int d = calendarUser.get(Calendar.DAY_OF_YEAR)
                    - calendarCurrent.get(Calendar.DAY_OF_YEAR);
            if (m == 0) {
                if (d == 0) {
                    // 当天
                    return format(milliseconds, HH_MM_24);
                }
            }
        }
        return format(milliseconds, YYYYMMDD);
    }

    /**
     * 聊天时间格式化
     *
     * @param milliseconds
     * @return
     */
    public static String dateFormatChatImpl(long milliseconds) {

        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds == 0 ? System.currentTimeMillis()
                : milliseconds);
        Calendar calendarCurrent = Calendar.getInstance();
        long current = System.currentTimeMillis();
        calendarCurrent.setTimeInMillis(current);
        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);
        int h = calendarUser.get(Calendar.HOUR_OF_DAY);
        boolean flag = h >= 0 && h < 12;
        String name = (flag ? " 上午 " : " 下午 ") + format(milliseconds, HH_MM_12);
        if (y == 0) {
            int m = calendarUser.get(Calendar.MONTH)
                    - calendarCurrent.get(Calendar.MONTH);
            int d = calendarUser.get(Calendar.DAY_OF_YEAR)
                    - calendarCurrent.get(Calendar.DAY_OF_YEAR);
            if (m == 0) {
                if (d == 0) {
                    // 当天
                    return name;
                } else if (d == -1) {
                    // 超过当天一天少于两天
                    return "昨天 " + name;
                } else if (d <= -2 && d > -7) {
                    // 超过两天少于七天
                    return getWeek(milliseconds) + name;
                } else {
                    // 超过7天少于一年
                    return format(milliseconds, MM_DD) + name;
                }
            } else {
                // 超过7天少于一年
                return format(milliseconds, MM_DD) + name;
            }
        } else if (y == -1) {
            int m = calendarUser.get(Calendar.MONTH)
                    - (calendarCurrent.get(Calendar.MONTH) + 12);
            if (m == -1) {
                int d = calendarUser.get(Calendar.DAY_OF_MONTH)
                        - (calendarCurrent.get(Calendar.DAY_OF_MONTH) + 31);
                if (d == -1) {
                    // 超过当天一天少于两天
                    return "昨天" + name;
                } else if (d <= -2 && d > -7) {
                    // 超过两天少于七天
                    return getWeek(milliseconds) + name;
                } else {
                    // 超过7天少于一年
                    return format(milliseconds, MM_DD) + name;
                }
            }
            return format(milliseconds, MM_DD) + name;
        }
        // 超过一年则显示年月日，格式为：15-9-16
        return format(milliseconds, YY_MM_DD) + name;
    }

    public static int getMonth(long timestamp) {
        Date date = new Date(timestamp);
        return date.getMonth();
    }

    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        try {
            // if (StringUtils.isNotBlank(date)) {
            // if (StringUtils.isBlank(format))
            // format = SimpleDateUtil.TIME_FORMAT;

            SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
            return sf.parse(date).getTime();
            // }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String getTipByTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        String dateString = formatter.format(time);
        // 获取系统时间 本地时间错误 可以在尝试的时候 将时间发送给服务器 服务器可以针对手机不同的客户端时间 来设定下次的 间隔时间 潜伏时间等等
        // 本地当前时间
        long localCurrentTime = System.currentTimeMillis();
        if (localCurrentTime < issueData) {
            // 如果系统时间 比真实的发布时间还小 说明客户端时间不可信
            // 对方传的时间也不可信
            if (time < issueData) {
                // 对方时间不可信任
                dateString = "?";
            } else {
                Date currentTime = new Date(time);
                formatter = new SimpleDateFormat(TIME_FORMAT_S, Locale.getDefault());
                dateString = formatter.format(currentTime);
            }
        } else {
            // 本地时间近似可信任
            if (time < issueData) {// 1分钟误差
                // 对方时间不可信任
                dateString = "?";
            } else if (time > localCurrentTime) {
                dateString = "1分钟前";
            } else {
                long timeGap = localCurrentTime - time;
                // Date DateGap = new Date(timeGap);
                // Date dateT = new Date(time);
                Date dateT = new Date(localCurrentTime);
                int CurrentH = dateT.getHours();
                int millisForDay = 24 * 60 * 60 * 1000;
                int millisForHour = 60 * 60 * 1000;
                int millisForMin = 60 * 1000;
                int ds = (int) ((timeGap) / millisForDay);
                int hs = (int) ((timeGap) / millisForHour);
                int hm = (int) ((timeGap) / millisForMin);
                if (ds > 1) {
                    formatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
                    dateString = formatter.format(time);
                } else if (ds == 1) {
                    if (hs - 24 > CurrentH) {
                        formatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
                        dateString = formatter.format(time);
                    } else {
                        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        dateString = "昨天" + formatter.format(time);
                    }
                } else if (hs > 0) {
                    if (CurrentH > hs) {
                        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        dateString = formatter.format(time);
                    } else {
                        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        dateString = "昨天" + formatter.format(time);
                    }

                } else if (hm > 0 && hm < 60) {
                    dateString = hm + "分钟前";
                } else {
                    dateString = 1 + "分钟前";
                }
            }
        }
        return dateString;
    }

    public static String getMonthStr(int month) {
        switch (month) {
            case 1:
                return "一月";
            case 2:
                return "二月";
            case 3:
                return "三月";
            case 4:
                return "四月";
            case 5:
                return "五月";
            case 6:
                return "六月";
            case 7:
                return "七月";
            case 8:
                return "八月";
            case 9:
                return "九月";
            case 10:
                return "十月";
            case 11:
                return "十一月";
            case 12:
                return "十二月";
            default:
                return "";
        }
    }

    enum TimeName {
        today, yesterday, the_day_before_yesterday, the_day_before_yesterday_more, tomorrow, the_day_after_tomorrow, the_day_after_tomorrow_more, last_month, last_month_more, next_month, next_month_more, last_year, last_year_more, next_year, next_year_more
    }

    enum HoursFormat {
        H12, H24
    }

    /**
     * 时段
     */
    enum TimeQuantum {
        /**
         * 凌晨:24点-6点
         **/
        wee_hours,
        /**
         * 上午:6点-12点
         **/
        forenoon,
        /**
         * 中午:12点-14点
         **/
        nooning,
        /**
         * 下午:14点-18点
         **/
        afternoon,
        /**
         * 晚上:18点-24点
         **/
        night
    }


    public static final boolean isToday(long serverTime, long when) {
        if (sCalendar == null) {
            sCalendar = Calendar.getInstance();
        }
        sCalendar.setTimeInMillis(when); ///动态时间
        int year = sCalendar.get(Calendar.YEAR);
        int month = sCalendar.get(Calendar.MONTH);
        int day = sCalendar.get(Calendar.DAY_OF_MONTH);
        sCalendar.setTimeInMillis(serverTime); //服务器时间
        return (year == sCalendar.get(Calendar.YEAR))
                && (month == sCalendar.get(Calendar.MONTH))
                && (day == sCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public static final boolean isThisYear(long serverTime, long when) {
        if (sCalendar == null) {
            sCalendar = Calendar.getInstance();
        }
        sCalendar.setTimeInMillis(when); ///动态时间
        int year = sCalendar.get(Calendar.YEAR);
        sCalendar.setTimeInMillis(serverTime); //服务器时间
        return (year == sCalendar.get(Calendar.YEAR));
    }

    /**
     * 将时间戳转换为时间
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 将时间转换为时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    /**
     * 返回yyyyMMdd时间
     */
    public static String getSimpleDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String str = sdf.format(new Date());
        return str;
    }


    /**
     * 返回yyyyMMddHHmmss时间
     *
     * @return
     */
    public static String getDateString() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String str = sdf.format(new Date());
        return str;
    }

    /**
     * 返回yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String getShowStandDatetime(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(date);
    }

    public static String getShowStandDatetimeWithoutSeconds(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 获得格式化日期
     *
     * @param date 传入yyyyMMdd
     * @return MMdd
     */
    public static String getShowDate(String date) {

        if (TextUtils.isEmpty(date) || date.length() < 8) {
            return "";
        }
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        return month + "-" + day;
    }

    /**
     * @param str 传入yyyyMMdd
     * @return yyyyMMdd
     */
    public static String getCompleteDate(String str) {

        String dateStr = "";

        if (TextUtils.isEmpty(str)) {
            return dateStr;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            Date date = sdf.parse(str);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dateStr = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateStr;

    }

    /**
     * 获得格式化时间
     *
     * @param str 传入HHmmss
     * @return
     */
    public static String getShowTime(String str) {

        String timeStr = "";

        if (TextUtils.isEmpty(str)) {
            return timeStr;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", Locale.CHINA);
            Date date = sdf.parse(str);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            timeStr = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String getShowShortTime(String time) {

        String timeStr = getShowTime(time);
        return timeStr.substring(0, timeStr.lastIndexOf(":"));
    }

    public static String getSimpleTime(String time) {

        if (TextUtils.isEmpty(time) || time.length() < 6) {
            return "";
        }
        String hour = time.substring(0, 2);
        String minute = time.substring(2, 4);
        return hour + ":" + minute;

    }

    /**
     * 获取时间差值，大于0表示未开始，小于0表示已经开始
     * @param nowtime
     * @param endtime
     * @return
     */
    public static long timeDifference(String nowtime, String endtime) {
        String nowTime=stampToDate(nowtime);
        String endTime=stampToDate(endtime);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            //系统时间转化为Date形式
            Date dstart = format.parse(nowTime);
            //活动结束时间转化为Date形式
            Date dend = format.parse(endTime);
            //算出时间差，用ms表示
            diff = dend.getTime() - dstart.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //返回时间差
        return diff;
    }

}
