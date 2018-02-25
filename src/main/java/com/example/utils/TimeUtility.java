package com.example.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;

@Component
public class TimeUtility {
    private Timestamp timestamp;
    private  Calendar calendar;

    public TimeUtility() {
        calendar = Calendar.getInstance();
        timestamp = new Timestamp(calendar.getTime().getTime());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}