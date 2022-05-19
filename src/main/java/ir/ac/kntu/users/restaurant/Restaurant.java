package ir.ac.kntu.users.restaurant;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.type.Rate;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.User;
import ir.ac.kntu.users.grocery.Grocery;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.util.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public class Restaurant extends Store implements Comparable<Restaurant> {
    private String subName;

    private CLI cli = new CLI();

    private Setter setter = new Setter();

    private final HashMap<Restaurant, ArrayList<Restaurant>> restaurantMap;

    public Restaurant(String username, HashPassword password, String name, String address, Duration hours, Range priceRange, ArrayList<Item> menu, int deliveryPayoutByOrder, int deliveryPayoutByHour) {
        super(username, password, name, address, hours, priceRange, menu, deliveryPayoutByOrder, deliveryPayoutByHour);

        restaurantMap = Program.getRestaurantMap();
    }

    public Restaurant(String subName, String address, Duration hours, Range priceRange, ArrayList<Item> menu, int deliveryPayoutByOrder, int deliveryPayoutByHour) {
        this.subName = subName;
        setAddress(address);
        setHours(hours);
        setPriceRange(priceRange);
        setMenu(menu);
        setRating(new Rate());
        setDeliveryPayoutByOrder(deliveryPayoutByOrder);
        setDeliveryPayoutByHour(deliveryPayoutByHour);

        restaurantMap = Program.getRestaurantMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getHours(), that.getHours()) && Objects.equals(getMenu(), that.getMenu())
                && Objects.equals(getDeliveryPayoutByHour(), that.getDeliveryPayoutByHour()) && Objects.equals(getDeliveryPayoutByOrder(), that.getDeliveryPayoutByOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getHours(), getMenu(), getDeliveryPayoutByHour(), getDeliveryPayoutByOrder());
    }

    @Override
    public String toString() {
        if (getName() == null){
            return  "Restaurant name: " + subName +
                    ", Address: " + getAddress() +
                    ", Working hours: " + getHours() +
                    ", Rating: " + getRating() +
                    ", Price Range: " + getPriceRange();
        }
        return "Restaurant name: " + getName() +
                ", Address: " + getAddress() +
                ", Working hours: " + getHours() +
                ", Rating: " + getRating() +
                ", Price Range: " + getPriceRange();
    }

    @Override
    public void handleUser() {
        int choice = 0;
        while (choice != 6){
            cli.getOutput().getMenu().printRestaurantMenu();
            choice = cli.inputInt();


            switch (choice){
                case 1:
                    showUser();
                    break;
                case 2:
                    ArrayList<Restaurant> restaurantList = restaurantMap.get(this);
                    restaurantList.add(newRestaurantBranch());
                    restaurantMap.put(this, restaurantList);
                    break;
                case 3:
                    selectRestaurant().configUser();
                    break;
                case 4:
                    selectRestaurant().showDeliveries();
                    break;
                case 5:
                    cli.showStoreReviews(this);
                    break;
                case 6:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    @Override
    public void showDeliveries() {
        ArrayList<Delivery> deliveriesOfMyRestaurant = new ArrayList<>();
        for (ArrayList<Store> restaurantForEachDelivery : Program.getDeliveryMap().values()){
            for (Store restaurant : restaurantForEachDelivery){
                if (restaurant.equals(this)){
                    deliveriesOfMyRestaurant.add(findInDeliveryMap(restaurantForEachDelivery));
                }
            }
        }
        deliveriesOfMyRestaurant.removeIf(Objects::isNull);
        cli.getOutput().printArrayInOrder(deliveriesOfMyRestaurant, false);
    }

    public Restaurant selectRestaurant() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        if (restaurantMap.containsKey(this)){
            restaurants.addAll(restaurantMap.get(this));
        }
        System.out.println("Select number of the branch: ");

        int input = 0;
        while (input > restaurants.size() || input <= 0){
            int i = 1;
            for (Restaurant restaurant : restaurants) {
                System.out.println(i + ": " + restaurant +
                        ", it pays a delivery " + restaurant.getDeliveryPayoutByOrder() +
                        " IRR by order and " + restaurant.getDeliveryPayoutByHour() + " IRR by hour");
                i++;
            }
            System.out.print(">> ");
            input = cli.inputInt();
        }

        return restaurants.get(input-1);
    }

    public Restaurant newRestaurantBranch(){
        int[] deliveryPayoutSetter = setter.deliveryPayoutSetter();
        return new Restaurant(getName(), setter.addressSetter(), setter.timeSetter(false), setter.rangeSetter(), createMenu(), deliveryPayoutSetter[0], deliveryPayoutSetter[1]);
    }

    private ArrayList<Item> createMenu(){
        ArrayList<Item> menu = new ArrayList<>();
        System.out.println("\nCreate the Menu:");

        int choice = 0;
        while (choice != 4){
            cli.getOutput().getMenu().printTheMenuMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    menu.add(createFood());
                    break;
                case 2:
                    removeItem(menu);
                    break;
                case 3:
                    cli.getOutput().printArrayInOrder(menu, false);
                    break;
                case 4:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
        return menu;
    }

    @Override
    public void configUser() {
        int choice = 0;
        while (choice != 6){
            cli.getOutput().getMenu().printEditRestaurantMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    if (this.getName() != null) {
                        this.setName(setter.nameSetter("restaurant"));
                    } else {
                        cli.getOutput().getError().error201();
                    }
                    break;
                case 2:
                    this.setAddress(setter.addressSetter());
                    break;
                case 3:
                    this.setHours(setter.timeSetter(false));
                    break;
                case 4:
                    this.setPriceRange(setter.rangeSetter());
                    break;
                case 5:
                    setMenu(createMenu());
                    break;
                case 6:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }

    }

    @Override
    public int compareTo(@NotNull Restaurant o) {
        return Comparators.rating.compare(this, o);
    }

    public static class Comparators {
        private static Comparator<Restaurant> rating = (Restaurant o1, Restaurant o2) -> o1.getRating().compareByRate(o2.getRating());
        private static Comparator<Restaurant> numOfComments = (Restaurant o1, Restaurant o2) -> o1.getRating().compareByNumOfRate(o2.getRating());

        public static Comparator<Restaurant> getRATING() {
            return rating;
        }

        public static Comparator<Restaurant> getNumOfComments() {
            return numOfComments;
        }
    }

}
