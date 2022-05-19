package ir.ac.kntu.type;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Time {
    private int hour;

    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time(Time time){
        hour = time.getHour();
        minute = time.getMinute();
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void increaseByMin(int min){
        minute += min;
        while (min >= 60){
            hour++;
            min -= 60;
            if (hour > 23){
                hour -= 24;
            }
        }
    }

    public int remainingTime(int min){
        Time due = new Time(hour, minute);
        due.increaseByMin(min);
        return differenceWithCurrentTime();
    }

    public int differenceWithCurrentTime(){
        int hourDiff = Time.getCurrentTime().getHour()-hour;
        int minDiff = Time.getCurrentTime().getMinute()-minute;
        return hourDiff*60 + minDiff;
    }

    public static Time getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+4:30"));
        String[] currentTime = formatter.format(new Date()).split(":");
        return new Time(Integer.parseInt(currentTime[0]), Integer.parseInt(currentTime[1]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Time time = (Time) o;
        return hour == time.hour && minute == time.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        if (hour >= 10 && minute < 10){
            return hour + ":0" + minute;
        } else if (hour < 10 && minute >= 10){
            return "0"+hour + ":" + minute;
        } else if (hour < 10){
            return "0"+hour + ":0" + minute;
        }
        return hour + ":" + minute;
    }

    public boolean isLater(Time compared) {
        LocalTime dis = LocalTime.parse(this.toString());
        LocalTime comparator = LocalTime.parse(compared.toString());

        if (this.inMidnight() && !compared.inMidnight()){
            return true;
        }

        if (!this.inMidnight() && compared.inMidnight()){
            return false;
        }

        return dis.isAfter(comparator);
    }

    public boolean inMidnight(){
        return LocalTime.parse(this.toString()).isBefore(LocalTime.parse("12:00"));
    }
}