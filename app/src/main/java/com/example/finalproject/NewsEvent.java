package com.example.finalproject;



public class NewsEvent {
    /**
     *
     * @param month
     * @param day
     * @return the url pf this date
     */
    public static String urlString(String month, String day) {
        return "https://history.muffinlabs.com/date/" + month + "/" + day;
    }

}
