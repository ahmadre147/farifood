package ir.ac.kntu.users.customer;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Time;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.util.CLI;

import java.util.ArrayList;

public class Order {
    private Store store;

    private ArrayList<Item> foods;

    private ArrayList<Item> clonedFoods;

    private int elapsedTime;

    private Time takenTime;

    private int price;

    private Delivery delivery;

    private boolean subscription = false;

    private CLI cli = new CLI();

    public Order(Store store, ArrayList<Item> foods, int elapsedTime, int price) {
        this.store = store;
        this.foods = foods;
        this.elapsedTime = elapsedTime;
        this.price = price;
        takenTime = Time.getCurrentTime();
        deliverySetter();
    }

    public Order(Store store, ArrayList<Item> items, int price, boolean isSubscribed) {
        this.store = store;
        foods = items;
        reduceOrdered(items);
        this.price = price;
        elapsedTime = 60;
        deliverySetter();
        deliveryDurationSetter();
        subscription = isSubscribed;
    }

    public Order(Store store, ArrayList<Item> foods, int elapsedTime, int price, boolean jUnit) {
        this.store = store;
        this.foods = foods;
        this.elapsedTime = elapsedTime;
        this.price = price;
        takenTime = Time.getCurrentTime();
        deliverySetter(jUnit);
    }

    private void deliveryDurationSetter() {
        store.setPeriodsPopularity(store.getLogisticWorkingHours().getAPeriod(this, store.getPeriodsPopularity(), store));
    }

    private void reduceOrdered(ArrayList<Item> items) {
        items.forEach(item -> item.setQuantity(item.getQuantity()-1));
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<Item> getFoods() {
        clonedFoods = new ArrayList<>();
        clonedFoods.addAll(foods);
        return clonedFoods;
    }

    public void changeElapsedTime() {
        System.out.println("New remaining time: ");
        elapsedTime = cli.inputInt();
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public boolean isInProgress(){
        Time due = new Time(takenTime);
        due.increaseByMin(elapsedTime);
        return due.isLater(Time.getCurrentTime());
    }

    public void setTakenTime(Time takenTime) {
        this.takenTime = takenTime;
    }

    @Override
    public String toString() {
        if (store instanceof Restaurant) {
            return "Order: " +
                    "restaurant = " + store +
                    ", foods = " + foods +
                    ", remaining time = " + takenTime.remainingTime(elapsedTime) +
                    ", price = " + price;
        }
        return "Order: " +
                "store = " + store +
                ", items = " + foods +
                ", remaining time = " + takenTime.remainingTime(elapsedTime) +
                ", price = " + price;
    }

    public String toString(boolean bool) {
        return "Order: " +
                "restaurant = " + store +
                ", foods = " + foods +
                ", price = " + price;
    }

    public void deliverySetter() {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        for (Delivery delivery : Program.getDeliveryMap().keySet()){
            if (Program.getDeliveryMap().get(delivery).contains(store)){
                deliveries.add(delivery);
            }
        }

        if (deliveries.size()==0){
            new CLI().getOutput().getError().error206();
            return;
        }

        System.out.println("Please select one of the available delivery guys to pick your order up:");
        cli.getOutput().printArrayInOrder(deliveries, true);
        delivery = deliveries.get(cli.inputInt()-1);
    }

    public void deliverySetter(boolean jUnit) {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        for (Delivery delivery : Program.getDeliveryMap().keySet()){
            if (Program.getDeliveryMap().get(delivery).contains(store)){
                deliveries.add(delivery);
            }
        }

        if (deliveries.size()==0){
            new CLI().getOutput().getError().error206();
            return;
        }

        delivery = deliveries.get(0);
    }

    public void removeFood() {
        System.out.println("Select the food you want to remove: ");
        cli.getOutput().printArrayInOrder(foods, true);
        foods.remove(cli.inputInt()-1);
    }

    public void addDeliveryFee(int fee){
        foods.add(new Item("Delivery Fee", 1, fee, true));
    }

    public boolean isSpecial() {
        return subscription;
    }
}
