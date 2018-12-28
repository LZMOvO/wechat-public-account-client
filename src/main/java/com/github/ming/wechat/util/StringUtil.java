package com.github.ming.wechat.util;

import java.util.Random;

/**
 * String工具类
 *
 * @author Zeming
 * @date 2018/1/28 13:33
 */
public class StringUtil {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String TAB = "  ";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";

    /**
     * 判断字符串是否是空白
     * ps.空白 = null or 空字符串
     *
     * @param str 待验证字符串
     * @return true=是空白 false=不是空白
     */
    public static boolean isBlank(String str) {
        return str == null || EMPTY.equals(str);
    }

    /**
     * 判断字符串是否全是空格
     *
     * @param str 待验证字符串
     * @return true=是 false=不是
     */
    public static boolean isAllSpace(String str) {
        return str.trim().length() == 0;
    }

    /**
     * 是否为无效字符串
     *
     * @param str 待验证字符串
     * @return true=是 false=不是
     */
    public static boolean isInvalidStr(String str) {
        return isBlank(str) || isAllSpace(str);
    }

    /**
     * 产生指定长度的随机字符串
     * 包括字母（区分大小写）与数字
     *
     * @param length 获取的字符串的长度
     * @return
     */
    public static String randomStr(int length) {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    str.append(String.valueOf((char) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    str.append(String.valueOf((char) result));
                    break;
                case 2:
                    str.append(String.valueOf(new Random().nextInt(10)));
                    break;
                default:
            }
        }
        return str.toString();
    }

}
