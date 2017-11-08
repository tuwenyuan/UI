package twy.com.ui.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @描述: 字符串工具类
 * @项目名: ugou
 * @包名: com.ugou88.ugou.common.utils
 * @类名: StringUtil
 * @作者: zuojie
 * @创建时间: 2016/3/15
 */
public class StringUtil {

    /*static {
        System.loadLibrary("ugou");
    }*/

    /** 手机号 */
    public final static String PHONE_PATTERN = "^[1][3-8]\\d{9}$";
    public final static String NUMBER_PATTERN = "^[0-9]\\d{10}$";
    /** 用户昵称 */
    public final static String NICKNAME_PATTERN = "^[a-zA-Z0-9_\\u4E00-\\u9FA5\\uF900-\\uFA2D]{3,12}$";

    public static final int BUF = 1024;

    public static boolean isNullOrEmpty(String str) {
        return null == str || str.trim().length() < 1;
    }

    public static boolean equals(String str1, String str2) {
        return null != str1 && null != str2 && str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return null != str1 && null != str2 && str1.equalsIgnoreCase(str2);
    }

    /**
     * InputStream转换成字符串
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer buf = new StringBuffer();
        if (null != in) {
            byte[] b = new byte[BUF];
            int n;
            while ((n = in.read()) != -1) {
                buf.append(new String(b, 0, n));
            }
        }
        return buf.toString();
    }

    /**
     * 替换字符串中特殊字符
     *
     * @param strData 源字符串
     * @return 替换了特殊字符后的字符串，如果strData为NULL，则返回空字符串
     */
    public static String encodeString(String strData) {
        if (strData == null) {
            return "";
        }
        return strData.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("'", "&apos;")
                .replaceAll("\"", "&quot;");
    }

    /**
     * 还原字符串中特殊字符
     *
     * @param strData strData
     * @return 还原后的字符串
     */
    public static String decodeString(String strData) {
        if (strData == null) {
            return "";
        }
        return strData.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&apos;", "'").replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&");
    }

    /**
     * 邮箱验证
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern pattern = Pattern.compile(strPattern);
            Matcher m = pattern.matcher(email);
            return m.matches();
        }
        return false;
    }

    /**
     */
    public static boolean isCard(String email) {
        if (!TextUtils.isEmpty(email)) {
            String strPattern = "(^\\d{17}.*$)|(^\\d{14}.*$)";
            Pattern pattern = Pattern.compile(strPattern);
            Matcher m = pattern.matcher(email);
            return m.matches();
        }
        return false;
    }


    /**
     * 邮箱验证
     * @return
     */
    public static boolean isPhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            String strPattern = "^1[\\d]{10}";
            Pattern pattern = Pattern.compile(strPattern);
            Matcher m = pattern.matcher(phone);
            return m.matches();
        }
        return false;
    }

    /**
     * <br>正则表达式匹配 </br>
     * @param patternStr
     * @param input
     * @return
     */
    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        if (!isNullOrEmpty(patternStr) && null != input) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(input);
            if (null != matcher && matcher.find()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 整数取万
     * @param num
     * @return
     * */
    public static String numToMyriad(int num){
        return numToMyriad((long)num);
    }
    /**
     * 整数取万
     * @param num
     * @return
     * */
    public static String numToMyriad(long num){
        String str = "";
        if (num >9999) {
            String temp = String.valueOf(num);
            String start = temp.substring(0, temp.length() - 4);
            String end = temp.substring(temp.length() - 4,temp.length() - 3);
            str = start;
            if (Integer.parseInt(end) > 0) {
                str +=  "."+end;
            }
            str += "万";
        }else {
            str = (String.valueOf(num));
        }
        return str;
    }

    /**
     * 获取距离
     * @param addr
     * @return
     */
    public static String getApart(double addr){
        double apart = addr;
        String address = "";
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("0.0");
        // 小于1千米时显示米
        if(apart <= 50.0d){
            address = "50m内";
        } else if (apart > 50.0d && apart < 1000.0d) {
            address = (int) apart + "m";
        } else if(apart >= 1000.0d && apart < 10000.0d){
            double aa = addr / 1000.0d;
            address = df1.format(aa) + "km";
        } else {
            double aa = addr / 1000.0d;
            address = df2.format(aa) + "km";
        }
        return address;
    }

    /**
     * 获取时长 s 保留一位小数  四舍五入
     * */
    public static float getDurationToFloat(long duration){
        float f = 0;
        try {
            BigDecimal b =  new BigDecimal(Long.toString(duration)) ;
            BigDecimal one = new BigDecimal("1000");
            f =  b.divide(one, 1, BigDecimal.ROUND_HALF_UP).floatValue();

        } catch (Exception e) {
            e.printStackTrace();
            f = 0;
        }
        return f;
    }

    /**
     * 获取时长，取整
     * */
    public static int getDurationToInt(long duration){
        int d = 0;
        try {
            BigDecimal b =  new BigDecimal(Long.toString(duration)) ;
            BigDecimal one = new BigDecimal("1000");
            d =  b.divide(one, 0, BigDecimal.ROUND_CEILING).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            d = 0;
        }
        return d;
    }

    /**
     * 格式化小数点后两位
     * @param price
     * @return
     */
    public synchronized static String getPriceByFormat(float price){
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        String priceNum = format.format(price);
        return priceNum;
    }

    /**
     * 格式化小数点后两位
     * @param price
     * @return
     */
    public synchronized static String getPriceByFormat(double price){
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        String priceNum = format.format(price);
        return priceNum;
    }

    /**
     * 格式化小数点后两位
     * @param money
     * @return
     */
    public synchronized static String getPriceByFormat(String money){
        if(TextUtils.isEmpty(money)){
            money = "0.00";
        }
        float price = Float.parseFloat(money);
        return getPriceByFormat(price);
    }



    /**
     * 获取List集合的值,例如：list.toString为[1,2,3,4] ,此方法的结果为：1,2,3,4
     * @param list
     * @return
     */
    public static String getListToString(List<String> list){
        return list.toString().replace("[", "").replace("]", "");
    }

    /**
     * 将手机号的中间四位替换为****
     * @param mobile
     * @return
     */
    public static String replaceMobile(String mobile){
        if(isMatcherFinded(PHONE_PATTERN,mobile)){
            return mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
        }else {
            return "";
        }
    }


    /**
     * 获取今年的年份
     * @return
     */
    public static int getThisYear(){
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;       //获取当前的年份
        return year;
    }


    /**
     * 将一个字符串转化成一个整数，如果为空，或者空字符串，则返回0
     * @param s
     * @return
     */
    public static float getIntFromCharSequence(CharSequence s){
        if(TextUtils.isEmpty(s)){
            return 0;
        }
        return Float.valueOf(s.toString().trim());
    }

    public static int getIntFromString(String str){
        if(TextUtils.isEmpty(str)){
            return 0;
        }
        return Integer.valueOf(str);
    }

    public static double getDoubleFromString(String str){
        if(TextUtils.isEmpty(str)){
            return 0;
        }
        return Double.valueOf(str);
    }


    /**
     * 设置edittext为金额输入框　
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }



    /**
     *  转换金额
     * @return
     */
    public static String convertMoneyToStr(double money){
//        ILog.d("传入的数据为:" + money);

        Locale.setDefault(Locale.US);
        DecimalFormat usFormat = new DecimalFormat("#,###,###,###,##0.00");

//        ILog.d("传出的数据为:" + usFormat.format(money));

        return usFormat.format(money) ;
    }
    public static String convert2xiaoshuToStr(double money){
//        ILog.d("传入的数据为:" + money);

        Locale.setDefault(Locale.US);
        DecimalFormat usFormat = new DecimalFormat("0.00");

//        ILog.d("传出的数据为:" + usFormat.format(money));

        return usFormat.format(money) ;
    }


    /**
     * 分享后的地址要进行二次编码
     * @param srcStr
     * @return
     */
    public static String encodeShareStr(String srcStr){
        String target = null;
        try {
            target = URLEncoder.encode(srcStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return target;
    }

    /**
     * 如果小数点后后面的为0 就去除小数点
     * @param s
     * @return
     */
    public static String getResult(Object s){
        if(s==null)
            return "0";
        String format = new DecimalFormat("0.00").format(Double.parseDouble(s.toString()));
        String[] strs = format.toString().split("\\.");
        if(strs.length>1) {
            if(strs[1].charAt(1)=='0'){
                if(strs[1].charAt(0)=='0'){
                    return strs[0];
                }else{
                    return strs[0]+"."+strs[1].charAt(0);
                }
            }
            return format;
        }
        return format.toString();
    }

}
