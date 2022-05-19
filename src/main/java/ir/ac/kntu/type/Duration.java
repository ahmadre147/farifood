package ir.ac.kntu.type;

import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.util.CLI;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Duration {
    private Time start;

    private Time end;

    public Duration(String duration){
        String[] splited = duration.split("[\\s+\\-:]", -1);
        int[] times = new int[4];
        int i = 0;
        for (String string : splited){
            if (!string.isEmpty() && isInt(string)){
                times[i++] = Integer.parseInt(string);
            }
        }
        start = new Time(times[0], times[1]);
        end = new Time(times[2], times[3]);
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    private boolean isInt(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean isOpen() {
        LocalTime now = LocalTime.parse(Time.getCurrentTime().toString());
        LocalTime start = LocalTime.parse(this.start.toString());
        LocalTime end = LocalTime.parse(this.end.toString());

        if (!start.isBefore(end)){
            if (now.isAfter(start)){
                end = LocalTime.parse("23:59");
            } else {
                start = LocalTime.parse("00:00");
            }
        }

        return start.isBefore(now) && end.isAfter(now);
    }

    public HashMap<Time, Integer> getAPeriod(Order order, HashMap<Time, Integer> timesMap, Store store){
        ArrayList<Time> times = new ArrayList<>();
        System.out.println("Please pick a period from below");
        int countPeriods = 1;
        Time startOfPeriod = new Time(start);
        while (end.isLater(startOfPeriod)){
            if (timesMap.containsKey(startOfPeriod)) {
                timesMap.put(startOfPeriod, timesMap.get(startOfPeriod)+1);
            } else {
                timesMap.put(startOfPeriod, 0);
            }
            times.add(startOfPeriod);
            System.out.print(countPeriods + ". " + startOfPeriod + " - ");
            startOfPeriod.increaseByMin(60);
            System.out.println(startOfPeriod + " (" +timesMap.get(startOfPeriod)+" times ordered)");
            countPeriods++;
        }
        Time theChosenOne = times.get(new CLI().inputInt()-1);
        int fee = 5000;
        if (timesMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey().equals(theChosenOne)){
            fee = 7500;
        } else if (order.isSpecial()){
            fee = 0;
        }
        if (store.getRunningPeriodInUse(theChosenOne) <= 2){
            order.setTakenTime(theChosenOne);
            timesMap.put(theChosenOne, timesMap.get(theChosenOne));
            order.addDeliveryFee(fee);
        } else {
            System.out.println("The period capacity is full. Pick something else.");
            return getAPeriod(order, timesMap, store);
        }
        return timesMap;
    }

    @Override
    public String toString() {
        return start+" - "+end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Duration)) {
            return false;
        }
        Duration duration = (Duration) o;
        return Objects.equals(getStart(), duration.getStart()) && Objects.equals(getEnd(), duration.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }
}
