package com.app.core.StudentInformationSystem.utils;

public class AppUtil {

    public static String getAlphaNumericString()
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        int max_length = 10;

        StringBuilder passwordBuilder = new StringBuilder(max_length);

        for (int i = 0; i < max_length; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());

            /* adding Character one by one in end of sb with random index. */
            passwordBuilder.append(AlphaNumericString.charAt(index));
        }

        return passwordBuilder.toString();
    }
}
