package org.easyspring.util;

public abstract class StringUtils {
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    public static String[] tokenizeToStringArray(String str,String token){
        String[] spiltStr = str.split(token);
        return spiltStr;
    }

    public static Object transferStringToObject(String string,Class<?> clazz){
        if (clazz == byte.class || clazz == Byte.class){
            return Byte.parseByte(string);
        }
        if (clazz == short.class || clazz == Short.class){
            return Short.parseShort(string);
        }
        if (clazz == int.class || clazz == Integer.class){
            return Integer.parseInt(string);
        }
        if (clazz == long.class || clazz == Long.class){
            return Long.parseLong(string);
        }
        if (clazz == double.class || clazz == Double.class){
            return Double.parseDouble(string);
        }
        if (clazz == float.class || clazz == Float.class){
            return Float.parseFloat(string);
        }
        if (clazz == boolean.class || clazz == Boolean.class){
            return Boolean.parseBoolean(string);
        }
        if (clazz == String.class){
            return string;
        }
        throw new RuntimeException("cannot parse this " + clazz.getName() +" type");
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        StringBuilder sb = new StringBuilder();
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sb.append(inString.substring(pos));
        // remember to append any characters to the right of a match
        return sb.toString();
    }
}
