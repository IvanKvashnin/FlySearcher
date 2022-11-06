package com.ikvashnin.bot;

public class Utils {
    public static String snakeToCamel(String inputString) {
        StringBuilder builder = new StringBuilder("camel_case");
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }
        return builder.toString();
    }
}