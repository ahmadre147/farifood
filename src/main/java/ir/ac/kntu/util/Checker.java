package ir.ac.kntu.util;

import ir.ac.kntu.type.Duration;

import java.util.regex.Pattern;

public class Checker {
    public Checker(){
    }

    public static boolean checkInt(String s) {
        return Pattern.compile("^[0-9]*$").matcher(s).matches();
    }

    public boolean checkUsername(String username) {
        return Pattern.compile("^[a-z][a-z0-9]{5,}").matcher(username).matches();
    }

    public boolean checkPassword(String password) {
        return Pattern.compile("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()_\\-=]{8,}").matcher(password).matches();
    }

    public boolean checkSchedule(String schedule) {
        return Pattern.compile("^[AN]{7}$").matcher(schedule).matches();
    }

    public boolean checkVehicle(String vehicle) {
        return Pattern.compile("^[M|C]$").matcher(vehicle).matches();
    }

    public boolean checkRestaurantName(String name) {
        return Pattern.compile("^[A-Z][\\sA-Za-z0-9]*").matcher(name).matches();
    }

    public boolean checkTimeFormat(Duration time) {
        return Pattern.compile("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]-([0-1]?[0-9]|2[0-3]):[0-5][0-9]$").matcher(time.getStart().toString() + "-" + time.getEnd().toString()).matches();
    }

    public boolean checkRange(String rangeModel) {
        return Pattern.compile("^\\${1,3}$").matcher(rangeModel).matches();
    }

    public boolean checkAddress(String address) {
        return Pattern.compile("^[A-Za-z0-9\\- /]*$").matcher(address).matches();
    }

    public boolean checkPhone(String phone) {
        return Pattern.compile("^\\d{11}$").matcher(phone).matches();
    }
}
