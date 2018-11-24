package org.lightning.particle.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by cook on 2017/5/31.
 */
public abstract class CamelCaseUtils {

    private static final char DEFAULT_SEPARATOR = '_';

    public static String toHumpWord(String s, char separator) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if (Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        if (i > 1 && s.charAt(i-1) == separator) {
                            // ignore
                        } else {
                            sb.append(separator);
                        }

                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String toHumpWord(String s) {
        return toHumpWord(s, DEFAULT_SEPARATOR);
    }

    public static String toCamelCase(String s, char separator) {
        if (s == null) {
            return null;
        }

        if (special.containsKey(s)) {
            return special.get(s);
        }

        //        s = s.toLowerCase();
        s = s.substring(0, 1).toLowerCase() + s.substring(1, s.length());

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == separator) {
                upperCase = true;
            } else {
                if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
//                    char temp = processUpperCase(s, c, i);
//                    sb.append(temp);
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    private static char processUpperCase(String s, char c, int i) {
        char temp = c;
        if (Character.isUpperCase(c)) {// 当前字符为大写
            if (i == 0) {// 第一个
                temp = Character.toLowerCase(c);
            } else {
                if (i+1 < s.length()) {// 非最后一个字母
                    if (Character.isUpperCase(s.charAt(i-1))) {// 前一个字母大写
                        if (Character.isUpperCase(s.charAt(i+1))) {// 且下一个为大写
                            temp = Character.toLowerCase(c);
                        }
                    } else {// 前一个字母小写

                    }
                } else {
                    if (Character.isUpperCase(s.charAt(i-1))) {// 前一个字母大写
                        temp = Character.toLowerCase(c);
                    }
                }
            }
        }
        return temp;
    }

    public static String toCamelCase(String s) {
        return toCamelCase(s, DEFAULT_SEPARATOR);
    }

    public static String toUpCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static final Map<String, String> special = new HashMap<>();

    static {
        special.put("ID", "id");
    }

}
