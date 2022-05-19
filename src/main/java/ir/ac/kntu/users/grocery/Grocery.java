package ir.ac.kntu.users.grocery;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.User;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.market.Market;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.util.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Grocery extends Store implements Comparable<Grocery> {

    private CLI cli = new CLI();

    private Setter setter = new Setter();

    public Grocery(String username, HashPassword password, String name, String address, Duration hours, Duration logisticWorkingHours, ArrayList<Item> menu, int deliveryPayoutByOrder, int deliveryPayoutByHour) {
        super(username, password, name, address, hours, menu, deliveryPayoutByOrder, deliveryPayoutByHour, logisticWorkingHours);
    }

    @Override
    public void handleUser() {
        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printGroceryMenu();
            choice = cli.inputInt();


            switch (choice){
                case 1:
                    showUser();
                    break;
                case 2:
                    ((Grocery) Program.getUserMap().get(getUsername())).editStore();
                    break;
                case 3:
                    ((Grocery)Program.getUserMap().get(getUsername())).showDeliveries();
                    break;
                case 4:
                    cli.showStoreReviews(this);
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }


    @Override
    public int compareTo(@NotNull Grocery o) {
        return Grocery.Comparators.rating.compare(this, o);
    }

    public static class Comparators {
        private static Comparator<Grocery> rating = (Grocery o1, Grocery o2) -> o1.getRating().compareByRate(o2.getRating());
        private static Comparator<Grocery> numOfComments = (Grocery o1, Grocery o2) -> o1.getRating().compareByNumOfRate(o2.getRating());


        public static Comparator<Grocery> getRATING() {
            return rating;
        }

        public static Comparator<Grocery> getNumOfComments() {
            return numOfComments;
        }
    }

}
