package ir.ac.kntu.users;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.type.Rate;
import ir.ac.kntu.type.Time;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Range;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.util.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class Store extends User {
    private String name;

    private String address;

    private Duration hours;

    private Duration logisticWorkingHours;

    private HashMap<Time, Integer> periodsPopularity;

    private HashMap<Time, Integer> runningPeriod;

    private Rate rating;

    private Range priceRange;

    private ArrayList<Item> menu;

    private ArrayList<Item> clonedMenu;

    private int deliveryPayoutByOrder;

    private int deliveryPayoutByHour;

    private CLI cli = new CLI();

    private Setter setter = new Setter();

    public Store(String username, HashPassword password, String name, String address, Duration hours, Range priceRange, ArrayList<Item> menu, int deliveryPayoutByOrder, int deliveryPayoutByHour) {
        super(username, password);
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.priceRange = priceRange;
        this.menu = menu;
        rating = new Rate();
        this.deliveryPayoutByHour = deliveryPayoutByHour;
        this.deliveryPayoutByOrder = deliveryPayoutByOrder;
    }

    public Store(String username, HashPassword password, String name, String address, Duration hours, ArrayList<Item> menu, int deliveryPayoutByOrder, int deliveryPayoutByHour, Duration logisticWorkingHours){
        super(username, password);
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.menu = menu;
        rating = new Rate();
        this.deliveryPayoutByHour = deliveryPayoutByHour;
        this.deliveryPayoutByOrder = deliveryPayoutByOrder;
        this.logisticWorkingHours = logisticWorkingHours;
        periodsPopularity = new HashMap<>();
    }

    public Store() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Duration getHours() {
        return hours;
    }

    public void setHours(Duration hours) {
        this.hours = hours;
    }

    public Rate getRating() {
        return rating;
    }

    public void setRating(Rate rating) {
        this.rating = rating;
    }

    public Range getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Range priceRange) {
        this.priceRange = priceRange;
    }

    public Duration getLogisticWorkingHours() {
        return logisticWorkingHours;
    }

    public HashMap<Time, Integer> getPeriodsPopularity() {
        return periodsPopularity;
    }

    public void setPeriodsPopularity(HashMap<Time, Integer> periodsPopularity) {
        this.periodsPopularity = periodsPopularity;
    }

    public void runningPeriod(Time startOf){
        if (runningPeriod.containsKey(startOf)){
            runningPeriod.put(startOf, runningPeriod.get(startOf) + 1);
        } else {
            runningPeriod.put(startOf, 1);
        }
    }

    public int getRunningPeriodInUse(Time startOf){
        if (!runningPeriod.containsKey(startOf)){
            return 0;
        }
        return runningPeriod.get(startOf);
    }

    public ArrayList<Item> getMenu() {
        clonedMenu = new ArrayList<>();
        clonedMenu.addAll(menu);
        return clonedMenu;
    }

    public void setMenu(ArrayList<Item> menu) {
        this.menu = menu;
    }

    public int getDeliveryPayoutByOrder() {
        return deliveryPayoutByOrder;
    }

    public void setDeliveryPayoutByOrder(int deliveryPayoutByOrder) {
        this.deliveryPayoutByOrder = deliveryPayoutByOrder;
    }

    public int getDeliveryPayoutByHour() {
        return deliveryPayoutByHour;
    }

    public void setDeliveryPayoutByHour(int deliveryPayoutByHour) {
        this.deliveryPayoutByHour = deliveryPayoutByHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Store that = (Store) o;
        return Objects.equals(name, that.getName()) && Objects.equals(address, that.getAddress()) && Objects.equals(hours, that.getHours()) && priceRange == that.getPriceRange();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, hours, rating, priceRange);
    }

    @Override
    public String toString() {
        if (priceRange == null){
            return "Store name: " + name +
                    ", Address: " + address +
                    ", Working hours: " + hours +
                    ", Rating: " + rating + "/5 (" + rating.getNumOfScores() + " ratings)";
        }
        return "Store name: " + name +
                ", Address: " + address +
                ", Working hours: " + hours +
                ", Rating: " + rating + "/5 (" + rating.getNumOfScores() + " ratings)" +
                ", Price Range: " + priceRange;
    }

    public void showDeliveries() {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        for (ArrayList<Store> stores : Program.getDeliveryMap().values()){
            for (Store store : stores){
                if (store.equals(this)){
                    deliveries.add(findInDeliveryMap(stores));
                }
            }
        }

        deliveries.removeIf(Objects::isNull);
        cli.getOutput().printArrayInOrder(deliveries, false);
    }

    public Delivery findInDeliveryMap(ArrayList<Store> storeList){
        for (Delivery delivery : Program.getDeliveryMap().keySet()){
            if (Program.getDeliveryMap().get(delivery).equals(storeList)){
                return delivery;
            }
        }
        return null;
    }

    public void editStore() {
        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printEditStoreMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    if (this.getName() != null) {
                        name = setter.nameSetter(getClass().getSimpleName());
                    } else {
                        cli.getOutput().getError().error201();
                    }
                    break;
                case 2:
                    address = setter.addressSetter();
                    break;
                case 3:
                    hours = setter.timeSetter(false);
                    break;
                case 4:
                    configItemList();
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    public void configItemList() {
        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printConfigItemListMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    getMenu().add(createItem());
                    break;
                case 2:
                    removeItem(getMenu());
                    break;
                case 3:
                    cli.getOutput().printArrayInOrder(getMenu(), false);
                    break;
                case 4:
                    configItem();
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    public void removeItem(ArrayList<Item> menu) {
        System.out.println("Please enter index of the item you want to remove: ");
        cli.getOutput().printArrayInOrder(menu, false);
        System.out.println(menu.size() + ": None");
        System.out.print(">> ");
        int choice = cli.inputInt()-1;

        if (choice == menu.size()-1){
            return;
        }

        menu.remove(choice);
    }

    public Item createItem(){
        System.out.print("Name of the Item: ");
        String name = cli.input();
        System.out.print("How much of this item is available? ");
        int quantity = cli.inputInt();
        System.out.print("Price of each item: ");
        int price = cli.inputInt();

        return new Item(name, quantity, price, true);
    }

    public Item createFood(){
        System.out.print("Name of the Food: ");
        String name = cli.input();
        System.out.print("How long will it take to prepare item (minutes): ");
        int elapsedTime = cli.inputInt();
        System.out.print("Price of the item (IRR): ");
        int price = cli.inputInt();

        return new Item(name, elapsedTime, price);
    }

    public void configItem() {
        cli.getOutput().printArrayInOrder(getMenu(), true);

        if (getMenu().size()==0){
            return;
        }

        Item selected = getMenu().get(cli.inputInt()-1);

        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printConfigItemMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    selected.setName(setter.nameSetter("Item"));
                    break;
                case 2:
                    System.out.println("Current Quantity: "+ selected.getQuantity());
                    System.out.print("New quantity: ");
                    selected.setQuantity(cli.inputInt());
                    break;
                case 3:
                    cli.getOutput().printArrayInOrder(getMenu(), false);
                    break;
                case 4:
                    getMenu().remove(selected);
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }
}
