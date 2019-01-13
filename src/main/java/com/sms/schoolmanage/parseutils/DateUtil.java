package com.sms.schoolmanage.parseutils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Integer getWeekOfDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0){
            w=7;
        }
        return w;
    }

    public static void main(String[] args) {
        System.out.println(getWeekOfDate());
    }
}
