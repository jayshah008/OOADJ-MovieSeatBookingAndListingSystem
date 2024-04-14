package src.model;

import java.io.Serializable;
import java.util.Calendar;

public class DateTime implements Serializable {

    private int minute;

    private int hour;

    private int day;

    private int date;

    private int month;

    private int year;
    private static final long serialVersionUID = 7L;

    public DateTime(int minute, int hour, int day, int date, int month, int year) {
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public DateTime() {
        this.minute = Calendar.MINUTE;
        this.hour = Calendar.HOUR;
        this.day = Calendar.DAY_OF_WEEK;
        this.date = Calendar.DAY_OF_MONTH;
        this.month = Calendar.MONTH;
        this.year = Calendar.YEAR;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getHour() {
        return this.hour;
    }

    public int getDate() {
        return this.date;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public void printTime() {
        System.out.print(this.getDate() + "/");
        System.out.print(this.getMonth() + "/");
        System.out.print(this.getYear() + "  ");

        switch (this.day) {
            case 1:
                System.out.print("Sunday  ");
                break;
            case 2:
                System.out.print("Monday  ");
                break;
            case 3:
                System.out.print("Tuesday  ");
                break;
            case 4:
                System.out.print("Wednesday  ");
                break;
            case 5:
                System.out.print("Thursday  ");
                break;
            case 6:
                System.out.print("Friday  ");
                break;
            case 7:
                System.out.print("Saturday  ");
                break;
            default:
                break;
        }

        System.out.println(String.format("%02d", this.getHour()) + String.format("%02d", this.getMinute()));
    }

    public String getTimeNow() {
        String timeNow = "";
        timeNow += this.getDate() + "/";
        timeNow += this.getMonth() + "/";
        timeNow += this.getYear() + "  ";

        switch (this.day) {
            case 1:
                timeNow += "Sunday  ";
                break;
            case 2:
                timeNow += "Monday  ";
                break;
            case 3:
                timeNow += "Tuesday  ";
                break;
            case 4:
                timeNow += "Wednesday  ";
                break;
            case 5:
                timeNow += "Thursday  ";
                break;
            case 6:
                timeNow += "Friday  ";
                break;
            case 7:
                timeNow += "Saturday  ";
                break;
            default:
                break;
        }

        timeNow += String.format("%02d", this.getHour());
        timeNow += String.format("%02d", this.getMinute());

        return timeNow;
    }

    public String getHolidayTimeNow() {
        String holidayTimeNow = " ";
        holidayTimeNow += this.getDate() + "/";
        holidayTimeNow += this.getMonth() + "/";
        holidayTimeNow += this.getYear() + "  ";

        return holidayTimeNow;
    }

    public static int getCurrentYear() {
        return Calendar.YEAR;
    }

    public static int getCurrentMonth() {
        return Calendar.MONTH;
    }

    public static int getCurrentDay() {
        return Calendar.DAY_OF_WEEK;
    }

    public static int getCurrentDate() {
        return Calendar.DATE;
    }

}
