package com.example.fitems.Classes;

import java.time.LocalDateTime;

public class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static MyDate getToday() {
        return new MyDate(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), LocalDateTime.now().getDayOfMonth());
    }

    public long getDateIdentificator() {
        return (long) this.year * this.month * this.day;
    }

    @Override
    public String toString() {
        return "MyDate {" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
