package ir.ac.kntu.users.delivery;

import java.util.Objects;

public class Schedule {
    private final boolean saturday;

    private final boolean sunday;

    private final boolean monday;

    private final boolean tuesday;

    private final boolean wednesday;

    private final boolean thursday;

    private final boolean friday;

    public Schedule(boolean saturday, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday) {
        this.saturday = saturday;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    public int getDaysAvailable() {
        int daysAvailable = 0;
        if (saturday){
            daysAvailable++;
        }
        if (sunday){
            daysAvailable++;
        }
        if (monday){
            daysAvailable++;
        }
        if (tuesday){
            daysAvailable++;
        }
        if (wednesday){
            daysAvailable++;
        }
        if (thursday){
            daysAvailable++;
        }
        if (friday){
            daysAvailable++;
        }
        return daysAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return saturday == schedule.saturday && sunday == schedule.sunday && monday == schedule.monday && tuesday == schedule.tuesday && wednesday == schedule.wednesday && thursday == schedule.thursday && friday == schedule.friday;
    }

    @Override
    public int hashCode() {
        return Objects.hash(saturday, sunday, monday, tuesday, wednesday, thursday, friday);
    }
}
