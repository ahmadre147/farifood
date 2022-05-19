package ir.ac.kntu.users.market;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.User;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.type.HashPassword;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Market extends Store implements Comparable<Market> {

    private CLI cli = new CLI();

    public Market(String username, HashPassword password, String name, String address, Duration hours, Duration logisticWorkHour, ArrayList<Item> menu, int deliveryPayoutByOrder, int deliveryPayoutByHour) {
        super(username, password, name, address, hours, menu, deliveryPayoutByOrder, deliveryPayoutByHour, logisticWorkHour);
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
                    ((Market) Program.getUserMap().get(getUsername())).editStore();
                    break;
                case 3:
                    ((Market) Program.getUserMap().get(getUsername())).showDeliveries();
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
    public int compareTo(@NotNull Market o) {
        return Market.Comparators.rating.compare(this, o);
    }

    public static class Comparators {
        private static Comparator<Market> rating = (Market o1, Market o2) -> o1.getRating().compareByRate(o2.getRating());
        private static Comparator<Market> numOfComments = (Market o1, Market o2) -> o1.getRating().compareByNumOfRate(o2.getRating());


        public static Comparator<Market> getRATING() {
            return rating;
        }

        public static Comparator<Market> getNumOfComments() {
            return numOfComments;
        }
    }

}
