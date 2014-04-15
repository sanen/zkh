package com.language.java.timzone;

import java.util.Calendar;
import java.util.SimpleTimeZone;

public class SimpleTimeZoneDemo {
    static SimpleTimeZone timeZone = null;

    public static void main(String[] args) {
        // Base GMT offset: -8:00
        // DST starts: at 2:00am in standard time
        // on the first Sunday in April
        // DST ends: at 2:00am in daylight time
        // on the last Sunday in October
        // Save: 1 hour
        timeZone = new SimpleTimeZone(-28800000,
                                      "America/Los_Angeles",
                                      Calendar.APRIL,
                                      1,
                                      -Calendar.SUNDAY,
                                      7200000,
                                      Calendar.OCTOBER,
                                      -1,
                                      Calendar.SUNDAY,
                                      7200000,
                                      3600000);
        System.out.println(timeZone);

    }
}
