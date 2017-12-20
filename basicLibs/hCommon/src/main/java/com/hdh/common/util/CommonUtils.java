package com.hdh.common.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hdh.common.util.app.AppUtil.LOG_TAG;

/**
 * 基本工具类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:34
 */

public class CommonUtils {

    /**
     * get proces name
     *
     * @param cxt
     * @param pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * close closeable:I/O stream
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * InputStream to byte[]
     */
    public static byte[] readStream(InputStream inStream) {
        byte[] returnByte = null;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream outSteam = null;
        try {
            outSteam = new ByteArrayOutputStream();
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            returnByte = outSteam.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(outSteam);
            close(inStream);
        }
        return returnByte;
    }

    public static void close(Closeable... ios) {
        for (Closeable io : ios) {
            try {
                if (io != null)
                    io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Takes the contents of this stream and writes it to the output stream
     *
     * @param baos
     * @param file
     */
    public static void writeTo(ByteArrayOutputStream baos, File file) {
        try {
            baos.writeTo(new FileOutputStream(file, false));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void createNewFile(File file) {
        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成随机字符串
     *
     * @param count 随机字符串位数
     * @return
     */
    public static String generalRandomString(int count) {

        String s = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            sb.append(s.charAt(random.nextInt(s.length())));
        }
        return sb.toString();

    }

    /**
     * 随机生成count 位数字字母字符串
     *
     * @param count 位数
     * @return
     */
    public static String generalRandomCharAndNumr(int count) {
        String val = "";

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }


    /**
     * 解析报文
     *
     * @param msg
     * @return
     */
    public static Map<String, String> parseMsg(String msg) {

        Map<String, String> map = new HashMap<String, String>();
        String[] kv = msg.split("&");
        try {
            for (String seq : kv) {

                String[] split = seq.split("=");
                String key = split[0];
                if (split.length == 1) {
                    map.put(key, "");
                } else {
                    String value = URLDecoder.decode(split[1], "UTF-8");
                    map.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return map;
    }


    /**
     * 金额元转分
     *
     * @param amt
     * @return
     */
    public static String convertAmtToCent(double amt) {

        DecimalFormat df = new DecimalFormat("#####################");
        double d = amt * 100;
        String str = df.format(d);
        return str;
    }

    public static String convertAmtToCent(String amt) {

        return convertAmtToCent(Double.parseDouble(amt));
    }

    /**
     * 金额分转元
     *
     * @param amt
     * @return
     */
    public static String convertAmtToYuan(String amt) {

        if (TextUtils.isEmpty(amt)) {
            return "";
        }
        double d = Double.parseDouble(amt) / 100;
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    public static String convertAmtToYuan(BigDecimal amt) {

        DecimalFormat df = new DecimalFormat("0.00");
        amt = amt.divide(new BigDecimal(100));
        return df.format(amt.doubleValue());
    }

    /**
     * 金额元格式化带2位小数的元
     *
     * @param amt
     * @return
     */
    public static String formatAmtToYuan(String amt) {

        double d = Double.parseDouble(amt);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    public static String formatAmtToYuan(BigDecimal amt) {

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(amt.doubleValue());
    }

    public static Double formatDouble(double value) {


        DecimalFormat df = new DecimalFormat("0.00");

        return Double.valueOf(df.format(value));
    }

    /**
     * 将double类型数据转换为百分比格式，并保留小数点前IntegerDigits位和小数点后FractionDigits
     *
     * @param value
     * @param FractionDigits
     * @return 0.048=4.80%
     */
    public static String getPercentFormat(double value, int FractionDigits) {
        NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
        String str = nf.format(value);
        return str;
    }


    /**
     * 显示银行卡格式 前四后六位显示 显示银行卡格式 前四后六位显示
     *
     * @param account
     * @return
     */
    public static String showBankAccountFormat(String account) {

        int leng = account.length();

        if (leng > 10) {

            String a = account.substring(0, 4);
            String b = account.substring(4, leng - 6).replaceAll(".", "*");
            String c = account.substring(leng - 6, leng);
            return a + b + c;
        }
        return account;
    }


    public static String maskAccountName(String name, int maskIndex) {

        int len = name.length();
        if (len > 3) {
            char[] c = name.toCharArray();
            maskIndex = 3;
            c[maskIndex] = '*';
            return new String(c);
        }
        if (len >= 1 && len < 4) {
            char[] c = name.toCharArray();
            maskIndex = len - 1;
            c[maskIndex] = '*';
            return new String(c);
        }

        return "";
    }


    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    public static boolean checkOverTime(Date startDate, Date endDate, int count) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        int temp = (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));

        if (temp > count) {
            return true;
        }
        return false;
    }


    /**
     * 过滤非数字
     *
     * @param str
     * @return
     */
    public static String filterUnNumber(String str) {
        // 只允数字
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        return m.replaceAll("").trim();
    }


    /**
     * 格式化身份证号码
     *
     * @param id_num
     * @return
     */
    public static String formatIdCard(String id_num) {
        String idNum;
        if (id_num.length() == 18) {
            idNum = id_num.substring(0, 6) + "  " + id_num.substring(6, 14) + "  " + id_num.substring(14, id_num.length());
        } else if (id_num.length() < 18 && id_num.length() > 14) {
            idNum = id_num.substring(0, 6) + "  " + id_num.substring(6, 14) + "  " + id_num.substring(14, id_num.length());
        } else if (id_num.length() <= 14 && id_num.length() > 6) {
            idNum = id_num.substring(0, 6) + "  " + id_num.substring(6, id_num.length());
        } else {
            idNum = id_num.substring(0, id_num.length());
        }
        return idNum;
    }


    /**
     * 过滤省市区
     *
     * @param str 省市区名称
     * @return
     */
    public static String filterChars(String str) {
        // 只允数字
        String regEx = "(省|县|市|区|地区|林区|盟|旗)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        str = m.replaceAll("").trim();
        LogUtil.i("CommonUtils", "str:" + str);
        return str;
    }

    /**
     * 金额格式化
     * <p>
     * <p>注意#和0的区别。
     * <p></>String sss=  new DecimalFormat("##.##").format(3.00) ——》sss为3
     *
     * @param s   金额
     * @param len 小数位数
     * @return 格式后的金额
     */
    public static String formatMoney(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (!s.contains(".")) {
            len = 0;
        }
        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {
            //根据len设置#个数
            StringBuffer buff = new StringBuffer();
            buff.append("###,##0.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            formater = new DecimalFormat(buff.toString());
        }

        return trimZero(formater.format(num));
    }

    /**
     * 去掉小数点后面无用的0
     *
     * @param str
     * @return
     */
    public static String trimZero(String str) {
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");//去掉后面无用的零
            str = str.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return str;
    }

    /**
     * 是否是整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * 金额去掉“,”
     *
     * @param s 金额
     * @return 去掉“,”后的金额
     */
    public static String delComma(String s) {
        String formatString = "";
        if (s != null && s.length() >= 1) {
            formatString = s.replaceAll(",", "");
        }

        return formatString;
    }


    /**
     * 去掉电话号码中特殊字符
     *
     * @param phoneNum
     * @return
     */
    public static String delPhoneNumRedundantChar(String phoneNum) {
        String str = "";
        if (!TextUtils.isEmpty(phoneNum)) {
            str = phoneNum.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ -]", "");
            str = str.replaceAll("^\\(?\\+?86\\)?", "");
        }

        return str;
    }


    public static boolean isSetEqual(Set set1, Set set2) {

        if (set1 == null && set2 == null) {
            return true; // Both are null
        }

        if (set1 == null || set2 == null || set1.size() != set2.size()
                || set1.size() == 0 || set2.size() == 0) {
            return false;
        }

        Iterator ite1 = set1.iterator();
        Iterator ite2 = set2.iterator();

        boolean isFullEqual = true;

        while (ite2.hasNext()) {
            if (!set1.contains(ite2.next())) {
                isFullEqual = false;
            }
        }

        return isFullEqual;
    }


    /**
     * 处理富文本 html显示
     * @param bodyHTML
     * @return
     */
    public static String getHtmlData(String bodyHTML) {
        if (TextUtils.isEmpty(bodyHTML)) return "";
        LogUtil.i(LOG_TAG, "bodyHTML:" + bodyHTML);
        try {
            String html = URLDecoder.decode(bodyHTML, "UTF-8");
            String head = "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                    "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                    "</head>";
            return "<html>" + head + "<body>" + html + "</body></html>";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
