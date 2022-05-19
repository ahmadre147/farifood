package ir.ac.kntu.users.delivery;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Rate;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.User;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.util.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Delivery extends User {
    private Vehicle vehicle;

    private TypeOfWage typeOfWage;

    private Schedule schedule;

    private Rate rating;

    private int numOfOrders = 0;

    private int deliveryFeeByOrder = 0;

    private int getDeliveryFeeByHour = 0;

    private CLI cli = new CLI();

    private final HashMap<Restaurant, ArrayList<Restaurant>> restaurantMap;

    private final HashMap<Delivery, ArrayList<Store>> deliveryMap;

    private final HashMap<Customer, Order> orderMap;

    private final HashMap<Customer, ArrayList<Order>> orderHistoryMap;

    public Delivery(String username, HashPassword password, Vehicle vehicle, TypeOfWage typeOfWage, Schedule schedule) {
        super(username, password);
        this.vehicle = vehicle;
        this.typeOfWage = typeOfWage;
        this.schedule = schedule;
        rating = new Rate();

        restaurantMap = Program.getRestaurantMap();
        deliveryMap = Program.getDeliveryMap();
        orderMap = Program.getOrderMap();
        orderHistoryMap = Program.getOrderHistoryMap();
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setTypeOfWage(TypeOfWage typeOfWage) {
        this.typeOfWage = typeOfWage;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setScore(int score){
        rating.setScore(score);
        numOfOrders++;
    }

    public Rate getRating() {
        return rating;
    }

    public int calculateWeeklyWage(){
        if (typeOfWage.equals(TypeOfWage.HOURLY)){
            return schedule.getDaysAvailable()*9;
        }
        return numOfOrders*schedule.getDaysAvailable();
    }

    @Override
    public String toString() {
        return "Username: "+getUsername()
                + ", Vehicle: "+vehicle.toString()
                + ", Weekly wage: "+calculateWeeklyWage()
                + ", Available days: "+schedule.getDaysAvailable()
                + ", Rating: "+rating+"/5";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Delivery delivery = (Delivery) o;
        return vehicle == delivery.vehicle && typeOfWage == delivery.typeOfWage && schedule == delivery.schedule;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, typeOfWage, schedule);
    }

    @Override
    public void handleUser() {
        int choice = 0;
        while (choice != 6){
            cli.getOutput().getMenu().printDeliveryMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    showUser();
                    break;
                case 2:
                    manageServices();
                    break;
                case 3:
                    showDeliveryRating();
                    break;
                case 4:
                    showDeliveryHistory();
                    break;
                case 5:
                    configUser();
                    break;
                case 6:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    public void manageServices() {
        int choice = 0;
        while (choice != 3){
            cli.getOutput().getMenu().printDeliveryServiceManager();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    signForService();
                    break;
                case 2:
                    removeService();
                    break;
                case 3:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    private void removeService() {
        if (!deliveryMap.containsKey(this)){
            cli.getOutput().getError().error203();
            return;
        }
        System.out.println("Enter the number of service you want to remove : ");
        cli.getOutput().printArrayInOrder(deliveryMap.get(this), true);
        int choice = cli.inputInt()-1;
        
        deliveryMap.get(this).remove(choice);
    }

    private void signForService() {
        ArrayList<Store> deliverList = new ArrayList<>();
        if (deliveryMap.containsKey(this)){
            deliverList = deliveryMap.get(this);
        }
        if (deliverList.size() >= 2){
            cli.getOutput().getError().error202();
        } else {
            Restaurant selectedChain = selectRestaurantChain();
            if (selectedChain==null){
                return;
            }
            deliverList.add(selectedChain.selectRestaurant());
            deliveryMap.put(this, deliverList);
        }
    }

    @Override
    public void configUser() {
        Setter setter = new Setter();
        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printChangeDeliveryInfoMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    this.setVehicle(setter.vehicleSetter());
                    break;
                case 2:
                    this.setTypeOfWage(setter.wageSetter());
                    break;
                case 3:
                    this.setSchedule(setter.scheduleSetter());
                    break;
                case 4:
                    changePassword();
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    public void showDeliveryHistory() {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order order : orderMap.values()) {
            if (order.getDelivery().equals(this)) {
                orders.add(order);
            }
        }
        for (ArrayList<Order> orderLists : orderHistoryMap.values()) {
            for (Order order : orderLists) {
                if (order.getDelivery().equals(this)) {
                    orders.add(order);
                }
            }
        }
        cli.getOutput().printArrayInOrder(orders, false);
    }

    public void showDeliveryRating() {
        if (this.getRating() == null){
            cli.getOutput().getError().error103();
            return;
        }
        System.out.println("Your rating is : " + this.getRating().getOverallScore() + "/5");
    }

    private Restaurant selectRestaurantChain(){
        ArrayList<Restaurant> chainList = new ArrayList<>(restaurantMap.keySet());
        if (chainList.size()==0){
            System.out.println("There is no available service to sign for!");
            return null;
        }
        System.out.println("Please select a chain: ");
        cli.getOutput().printArrayInOrder(chainList, true);
        return chainList.get(cli.inputInt()-1);
    }
}
