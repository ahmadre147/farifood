package ir.ac.kntu;

import ir.ac.kntu.type.Duration;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.users.Admin;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.delivery.Schedule;
import ir.ac.kntu.users.delivery.TypeOfWage;
import ir.ac.kntu.users.delivery.Vehicle;
import ir.ac.kntu.users.grocery.Grocery;
import ir.ac.kntu.users.market.Market;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Range;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.users.User;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.util.Preload;

import java.util.*;

public class Program {
    private final CLI cli = new CLI();

    private final SignUp signup = new SignUp();

    private final SignIn signin = new SignIn();

    private final static HashMap<String, User> USER_MAP = new HashMap<>();

    private final static HashMap<Restaurant, ArrayList<Restaurant>> RESTAURANT_MAP = new HashMap<>();

    private final static HashMap<Delivery, ArrayList<Store>> DELIVERY_MAP = new HashMap<>();

    private final static HashMap<Customer, Order> ORDER_MAP = new HashMap<>();

    private final static HashMap<Customer, ArrayList<Order>> ORDER_HISTORY_MAP = new HashMap<>();

    public Program(){
        USER_MAP.put("admin", new Admin());
        new Preload();
        handleTheOption();
    }

    public void handleTheOption(){
        int choice = 0;
        while (choice != 3){
            cli.getOutput().getMenu().printMainMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    signin.login();
                    break;
                case 2:
                    signup.signup();
                    break;
                case 3:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    public static HashMap<String, User> getUserMap() {
        return USER_MAP;
    }

    public static HashMap<Customer, ArrayList<Order>> getOrderHistoryMap() {
        return ORDER_HISTORY_MAP;
    }

    public static HashMap<Customer, Order> getOrderMap() {
        return ORDER_MAP;
    }

    public static HashMap<Delivery, ArrayList<Store>> getDeliveryMap() {
        return DELIVERY_MAP;
    }

    public static HashMap<Restaurant, ArrayList<Restaurant>> getRestaurantMap() {
        return RESTAURANT_MAP;
    }
}
