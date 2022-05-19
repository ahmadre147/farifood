package ir.ac.kntu.util;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.users.User;
import ir.ac.kntu.users.delivery.Schedule;
import ir.ac.kntu.users.delivery.TypeOfWage;
import ir.ac.kntu.users.delivery.Vehicle;
import ir.ac.kntu.users.restaurant.Range;

import java.util.HashMap;

public class Setter {
    private CLI cli;
    
    private Checker checker;
    
    public Setter(){
        cli = new CLI();
        checker = new Checker();
    }
    
    public Duration timeSetter(boolean ifLogistic) {
        if (ifLogistic){
            System.out.print("Logistic ");
        }

        Duration time = null;
        do {
            if (time != null){
                cli.getOutput().getError().error105("Time");
            }
            System.out.print("Working hours (e.g. 8:00 - 23:30): ");
            time = new Duration(cli.input());
        } while(!checker.checkTimeFormat(time));

        return time;
    }

    public String addressSetter() {
        System.out.print("Address: ");
        String address = cli.input();
        while (!checker.checkAddress(address)){
            cli.getOutput().getError().error106();
            System.out.print("Address: ");
            address = cli.input();
        }
        return address;
    }

    public String nameSetter(String type) {
        System.out.printf("Name of the %s: ", type);
        String name = cli.input();
        while(!checker.checkRestaurantName(name)){
            cli.getOutput().getError().error105("Name");
            System.out.printf("Name of the %s: ", type);
            name = cli.input();
        }
        return name;
    }


    public String usernameSetter() {
        System.out.print("Username: ");
        String username = cli.input();

        while (Program.getUserMap().containsKey(username)){
            cli.getOutput().getError().error108();
            System.out.print("Username: ");
            username = cli.input();
        }

        while (!checker.checkUsername(username)){
            cli.getOutput().getError().error107();
            System.out.print("Username: ");
            username = cli.input();

            while (Program.getUserMap().containsKey(username)){
                cli.getOutput().getError().error108();
                System.out.print("Username: ");
                username = cli.input();
            }
        }

        return username;
    }

    public HashPassword passwordSetter() {
        cli.getOutput().getError().error109();
        String password = cli.input();
        while (!checker.checkPassword(password)){
            cli.getOutput().getError().error109();
            password = cli.input();
        }
        return new HashPassword(password);
    }


    public Schedule scheduleSetter() {
        System.out.print("Enter your schedule of availability in standard format (--help for help): ");
        String schedule = cli.input().strip();
        while (!checker.checkSchedule(schedule)){
            if (schedule.equals("--help")){
                cli.getOutput().getMenu().printScheduleSettingHelp();
            } else {
                cli.getOutput().getError().error104();
            }
            System.out.print("Enter your schedule of availability in standard format (--help for help): ");
            schedule = cli.input().strip();
        }
        return new Schedule(schedule.charAt(0) == 'A', schedule.charAt(1) == 'A', schedule.charAt(2) == 'A', schedule.charAt(3) == 'A', schedule.charAt(4) == 'A', schedule.charAt(5) == 'A', schedule.charAt(6) == 'A');
    }

    public TypeOfWage wageSetter() {
        TypeOfWage out = null;
        System.out.print("Do you want to get paid per delivery(1) or per hour(2)? : ");
        while (out == null) {
            int wage = cli.inputInt();

            switch (wage){
                case 1:
                    out = TypeOfWage.PER_ORDER;
                    break;
                case 2:
                    out = TypeOfWage.HOURLY;
                    break;
                default:
                    cli.getOutput().getError().error104();
            }
        }

        return out;
    }

    public Vehicle vehicleSetter() {
        Vehicle out = null;
        System.out.print("Is your vehicle car or motorcycle? (M/C): ");
        while (out == null) {
            String vehicle = cli.input().strip();
            while (!checker.checkVehicle(vehicle)){
                cli.getOutput().getError().error104();
                System.out.print("Is your vehicle car or motorcycle? (M/C): ");
                vehicle = cli.input().strip();
            }

            switch (vehicle){
                case "M":
                    out = Vehicle.BIKE;
                    break;
                case "C":
                    out = Vehicle.CAR;
                    break;
                default:
                    throw new IllegalStateException("Invalid input. Please stay on the options.");
            }
        }

        return out;
    }


    public Range rangeSetter() {
        Range out = null;
        System.out.print("Price Range ($/$$/$$$): ");
        while (out == null) {
            String rangeModel = cli.input().strip();
            while (!checker.checkRange(rangeModel)){
                cli.getOutput().getError().error104();
                System.out.print("Price Range ($/$$/$$$): ");
                rangeModel = cli.input().strip();
            }
            switch (rangeModel){
                case "$":
                    out = Range.ECONOMIC;
                    break;
                case "$$":
                    out = Range.MEDIUM;
                    break;
                case "$$$":
                    out = Range.LUXURY;
                    break;
                default:
                    throw new IllegalStateException("Invalid input. Please try again.");
            }
        }
        return out;
    }

    public String phoneSetter() {
        System.out.print("Phone Number (e.g. 09xxxxxxxxx): ");
        String phoneNumber = cli.input();
        while (!checker.checkPhone(phoneNumber)){
            cli.getOutput().getError().error105("Phone Number");
            System.out.print("Phone Number (e.g. 09xxxxxxxxx): ");
            phoneNumber = cli.input();
        }
        return phoneNumber;
    }

    public int[] deliveryPayoutSetter() {
        int[] deliveryPayout = new int[2];
        System.out.print("How much do you pay the delivery guy for an hour?  ");
        deliveryPayout[0] = cli.inputInt();
        System.out.print("How much do you pay the delivery guy for an order?  ");
        deliveryPayout[1] = cli.inputInt();
        return deliveryPayout;
    }
}
