package ir.ac.kntu.users.customer;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Rate;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.grocery.Grocery;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.users.User;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.util.Checker;
import ir.ac.kntu.type.HashPassword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Stream;

public class Customer extends User {
    private String phoneNumber;

    private String address;

    private boolean subscription = false;

    private CLI cli = new CLI();

    private final HashMap<String, User> userMap;

    private final HashMap<Restaurant, ArrayList<Restaurant>> restaurantMap;

    private final HashMap<Delivery, ArrayList<Store>> deliveryMap;

    private final HashMap<Customer, Order> orderMap;

    private final HashMap<Customer, ArrayList<Order>> orderHistoryMap;

    public Customer(String username, HashPassword password, String phoneNumber, String address) {
        super(username, password);
        this.phoneNumber = phoneNumber;
        this.address = address;

        userMap = Program.getUserMap();
        restaurantMap = Program.getRestaurantMap();
        deliveryMap = Program.getDeliveryMap();
        orderMap = Program.getOrderMap();
        orderHistoryMap = Program.getOrderHistoryMap();
    }

    @Override
    public void handleUser() {
        int choice = 0;
        while (choice != 7){
            cli.getOutput().getMenu().printCustomerMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    showUser();
                    break;
                case 2:
                    order();
                    break;
                case 3:
                    orderSituation();
                    break;
                case 4:
                    orderHistory();
                    break;
                case 5:
                    rate();
                    break;
                case 6:
                    configUser();
                    break;
                case 7:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    @Override
    public void configUser() {
        int choice = 0;
        while (choice != 4){
            cli.getOutput().getMenu().printConfigCustomerMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    changePassword();
                    break;
                case 2:
                    changePhoneNumber();
                    break;
                case 3:
                    changeAddress();
                    break;
                case 4:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    private void changeAddress() {
        Checker checker = new Checker();
        System.out.print("New Address: ");
        String address = cli.input();
        while (!checker.checkAddress(address)){
            System.out.println("Invalid form of address. Please try again.");
            System.out.print("New Address: ");
            address = cli.input();
        }
        this.address = address;
    }

    private void changePhoneNumber() {
        Checker checker = new Checker();
        System.out.print("New phone number: ");
        String phoneNumber = cli.input();
        while (!checker.checkPhone(phoneNumber)){
            System.out.println("Invalid form of phone number. Please try again.");
            System.out.print("New phone number: ");
            phoneNumber = cli.input();
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() +
                ", Phone number: " + phoneNumber +
                ", Address: " + address;
    }

    public void order() {
        int choice = 0;
        while (choice != 3) {
            cli.getOutput().getMenu().printOrderTypeMenu();
            choice = cli.inputInt();

            switch (choice) {
                case 1:
                    Order orderRestaurant = orderRestaurant();
                    if (orderRestaurant != null){
                        orderMap.put(this, orderRestaurant);
                    }
                    break;
                case 2:
                    Order orderStore = orderStore();
                    if (orderStore != null){
                        orderMap.put(this, orderStore);
                    }
                    break;
                case 3:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    private Order orderStore() {
        Store store = selectAvailableStore();

        if (store==null){
            return null;
        }

        ArrayList<Item> items = selectItems(store);
        if (items.size()==0){
            System.out.println("Order cancelled!");
            return null;
        }

        subscribe();

        return new Order(store, items, calculateCost(items), subscription);
    }

    private boolean subscribe() {
        System.out.print("Do you want special subscription? (App special, no delivery fee, pay 200000 IRR/month) (Y/n)  ");
        return cli.inputBoolean();
    }

    private Store selectAvailableStore() {
        ArrayList<Store> stores = new ArrayList<>();

        userMap.values().forEach(user -> {
            if (user instanceof Store){
                stores.add((Store) user);
            }
        });

        stores.removeIf(store -> !store.getHours().isOpen());
        
        if (stores.size()==0){
            System.out.println("There are no open stores at the moment!");
            return null;
        }
        System.out.println("Please select between the below stores: ");
        cli.getOutput().printArrayInOrder(stores, true);
        
        return stores.get(cli.inputInt()-1);
    }

    private Order orderRestaurant() {
        Restaurant selectedRestaurant = selectAvailableRestaurant();

        if (selectedRestaurant==null){
            System.out.println("There is no open restaurant right now!");
            return null;
        }

        ArrayList<Item> foods = selectItems(selectedRestaurant);
        if (foods.size()==0){
            System.out.println("Order cancelled!");
            return null;
        }

        return new Order(selectedRestaurant, foods, calculateEpTime(foods), calculateCost(foods));
    }


    public void orderHistory() {
        updateHistory();
        cli.getOutput().printArrayInOrder(orderHistoryMap.get(this), false);
    }

    public void orderSituation() {
        if (!orderMap.containsKey(this)) {
            cli.getOutput().getError().error205();
            return;
        }
        Order order = orderMap.get(this);
        if (order.isInProgress()) {
            System.out.println(order);
        } else {
            System.out.println("Order handed over to the delivery!");
            System.out.println(order.toString(true));
            updateHistory();
        }
    }


    public void updateHistory(){
        ArrayList<Order> histories = new ArrayList<>();
        if (orderHistoryMap.containsKey(this)){
            histories = orderHistoryMap.get(this);
        }
        for (Order order : orderMap.values()){
            if (!order.isInProgress()){
                orderMap.remove(this, order);
                histories.add(order);
            }
        }
        orderHistoryMap.put(this, histories);
    }

    public void rate() {
        updateHistory();
        if (!orderHistoryMap.containsKey(this)) {
            cli.getOutput().getError().error204();
            return;
        }
        ArrayList<Order> customerOrders = orderHistoryMap.get(this);

        if (customerOrders.size()==0){
            System.out.println("You have no order delivered in your history to rate!");
            return;
        }
        System.out.println("Please select index of order you want to review: ");
        cli.getOutput().printArrayInOrder(customerOrders, true);
        int choice = cli.inputInt()-1;
        Order theOrder = customerOrders.get(choice);
        review(theOrder);

        System.out.print("Please enter the rating of delivery (1 to 5): ");
        choice = cli.inputInt();
        theOrder.getDelivery().setScore(choice);
    }


    private Restaurant selectAvailableRestaurant(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (ArrayList<Restaurant> restaurantLists : restaurantMap.values()){
            restaurants.addAll(restaurantLists);
        }
        restaurants.removeIf(restaurant -> !isOpen(restaurant));
        sortRestaurantList(restaurants);
        if (restaurants.size()==0){
            return null;
        }
        System.out.println("Please select between available restaurants: ");
        cli.getOutput().printArrayInOrder(restaurants, true);
        return restaurants.get(cli.inputInt()-1);
    }

    private boolean isOpen(Restaurant restaurant){
        return restaurant.getHours().isOpen();
    }

    private void sortRestaurantList(ArrayList<Restaurant> restaurants) {
        int choice = 0;
        while (choice<1 || choice>5){
            cli.getOutput().getMenu().printSortRestaurantMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    restaurants.sort(Restaurant.Comparators.getRATING());
                    Collections.reverse(restaurants);
                    break;
                case 2:
                    restaurants.sort(Restaurant.Comparators.getRATING());
                    break;
                case 3:
                    restaurants.sort(Restaurant.Comparators.getNumOfComments());
                    Collections.reverse(restaurants);
                    break;
                case 4:
                    restaurants.sort(Restaurant.Comparators.getNumOfComments());
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<Item> selectItems(Store selectedStore) {
        String typeOf;
        if (selectedStore instanceof Restaurant){
            typeOf = "food";
        } else {
            typeOf = "item";
        }

        if (selectedStore.getMenu().size()==0) {
            System.out.println("This " + typeOf + " does not offer anything at the moment");
            return new ArrayList<>();
        }
        
        ArrayList<Item> foodList = new ArrayList<>();
        System.out.println("Select number of the "+ typeOf +" you want to order (0 to finish order): ");
        cli.getOutput().printArrayInOrder(selectedStore.getMenu(), true);
        int choice = cli.inputInt()-1;
        if (choice==-1){
            System.out.println("You did not select anything to your order!");
            return foodList;
        }
        foodList.add(selectedStore.getMenu().get(choice));
        while (choice!=0){
            System.out.print(">> ");
            choice = cli.inputInt()-1;
            if (choice >= selectedStore.getMenu().size() || choice<0){
                cli.getOutput().getError().error101();
                continue;
            }
            foodList.add(selectedStore.getMenu().get(choice));
        }
        return foodList;
    }

    public int calculateEpTime(ArrayList<Item> foods){
        int epTime = 0;
        for (Item food : foods){
            if (food.getElapsedTime() > epTime){
                epTime = food.getElapsedTime();
            }
        }
        return epTime;
    }

    public int calculateCost(ArrayList<Item> foods){
        int cost = 0;
        for (Item food : foods){
            cost += food.getPrice();
        }
        return cost;
    }

    private void review(Order order) {
        System.out.print("What was your overall score of this restaurant (1 to 5): ");
        int overall = cli.inputInt();

        System.out.print("Comment: ");
        String comment = cli.input();
        order.getStore().setRating(new Rate(this, overall, comment));
        System.out.print("Do you want to add any specific review on any food of your order? (Y): ");
        String reviewFood = cli.input().strip();
        while (!reviewFood.equals("Y") && !reviewFood.equals("y")){
            cli.getOutput().getError().error104();
            System.out.print("Do you want to add any specific review on any food of your order? (Y): ");
            reviewFood = cli.input().strip();
        }
        int review = reviewFood(order);
        while(review != 0){
            review = reviewFood(order);
        }
    }


    private int reviewFood(Order order) {
        System.out.println("Which of food in your order you want to review? (Enter 0 to return)");
        cli.getOutput().printArrayInOrder(order.getFoods(), true);
        int choice = cli.inputInt()-1;
        if (choice==0){
            return 0;
        }
        reviewTheFood(order,order.getFoods().get(choice));
        return choice;
    }

    private void reviewTheFood(Order order, Item food) {
        System.out.print("What is your given score to this food? (1 to 5)");
        int score = cli.inputInt();

        System.out.println("Comment: ");
        String comment = cli.input();
        Store restaurant = order.getStore();
        ArrayList<Item> menu = restaurant.getMenu();
        menu.get(menu.indexOf(food)).setRate(new Rate(this, score, comment));
    }
}
