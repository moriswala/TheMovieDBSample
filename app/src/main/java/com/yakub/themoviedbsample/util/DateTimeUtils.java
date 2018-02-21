package com.yakub.themoviedbsample.util;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

  /**
   * Converts epoch time to relative time span.
   *
   * @param time time epoch in seconds. i.e: 1496491779000
   * @return relative time span compared with current. i.e: 5 minutes ago
   */
  public static String formatRelativeTime(long time) {
    return DateUtils.getRelativeTimeSpanString(time , System.currentTimeMillis(),
        android.text.format.DateUtils.MINUTE_IN_MILLIS).toString();
  }

  public static String formatDate(Date date) {
    String pattern = "dd-MMM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    try{
      return simpleDateFormat.format(date);
    }catch (Exception e){
    }
    return simpleDateFormat.format(new Date());
  }

  public static Date parseDate(String serverDateStr){
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    try{
      return simpleDateFormat.parse(serverDateStr);

    }catch (Exception e){

    }
    return new Date();
  }
}
