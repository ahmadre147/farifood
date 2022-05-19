package ir.ac.kntu;

import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.delivery.Schedule;
import ir.ac.kntu.users.delivery.TypeOfWage;
import ir.ac.kntu.users.delivery.Vehicle;
import ir.ac.kntu.users.grocery.Grocery;
import ir.ac.kntu.users.market.Market;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.users.User;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.util.Setter;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUp {
    private CLI cli = new CLI();

    private Setter setter = new Setter();

    public SignUp(){
    }

    public void signup() {
        cli.getOutput().getMenu().printSignupMenu();
        int choice = cli.inputInt();

        switch (choice) {
            case 1:
                signupAsCustomer();
                break;
            case 2:
                signupAsStore();
                break;
            case 3:
                signupAsDelivery();
                break;
            case 4:
                break;
            default:
                cli.getOutput().getError().error101();
                break;
        }
    }

    private User signupTheUser(){
        return new User(setter.usernameSetter(), setter.passwordSetter());
    }

    private void signupAsCustomer() {
        User user = signupTheUser();
        String phoneNumber = setter.phoneSetter();
        String address = setter.addressSetter();

        Program.getUserMap().put(user.getUsername(), new Customer(user.getUsername(), user.getPassword(), phoneNumber, address));
    }

    private void signupAsStore() {
        User user;
        switch (setUserType()){
            case 1:
                user = signupTheUser();
                Program.getUserMap().put(user.getUsername(), newRestaurant(user));
                break;
            case 2:
                user = signupTheUser();
                Program.getUserMap().put(user.getUsername(), newMarket(user));
                break;
            case 3:
                user = signupTheUser();
                Program.getUserMap().put(user.getUsername(), newGroceryStore(user));
                break;
            default:
                break;
        }
    }

    private int setUserType() {
        int choice = 0;
        while (choice != 4){
            cli.getOutput().getMenu().printUserTypeSetterMenu();
            choice = cli.inputInt();

            if (choice>0 && choice<4) {
                return choice;
            }

            cli.getOutput().getError().error101();
        }

        return choice;
    }

    private Restaurant newRestaurantBranch(String name){
        int[] deliveryPayoutSetter = setter.deliveryPayoutSetter();
        return new Restaurant(name, setter.addressSetter(), setter.timeSetter(false), setter.rangeSetter(), createMenu(), deliveryPayoutSetter[0], deliveryPayoutSetter[1]);
    }

    private Restaurant newRestaurant(User user){
        String name = setter.nameSetter("Restaurant");
        Restaurant restaurant = newRestaurantBranch(name);
        Restaurant head = new Restaurant(user.getUsername(), user.getPassword(), name, restaurant.getAddress(), restaurant.getHours(), restaurant.getPriceRange(), restaurant.getMenu(), restaurant.getDeliveryPayoutByOrder(), restaurant.getDeliveryPayoutByHour());
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant);
        Program.getRestaurantMap().put(head, restaurantList);
        return head;
    }

    private Market newMarket(User user){
        int[] deliveryPayoutSetter = setter.deliveryPayoutSetter();
        String name = setter.nameSetter("Market");
        return new Market(user.getUsername(), user.getPassword(), name, setter.addressSetter(), setter.timeSetter(false), setter.timeSetter(true), createItemList(), deliveryPayoutSetter[0], deliveryPayoutSetter[1]);
    }

    private Grocery newGroceryStore(User user){
        int[] deliveryPayoutSetter = setter.deliveryPayoutSetter();
        String name = setter.nameSetter("Grocery Store");
        return new Grocery(user.getUsername(), user.getPassword(), name, setter.addressSetter(), setter.timeSetter(false), setter.timeSetter(true), createItemList(), deliveryPayoutSetter[0], deliveryPayoutSetter[1]);
    }

    private void signupAsDelivery() {
        User user = signupTheUser();
        Vehicle vehicle = setter.vehicleSetter();
        TypeOfWage typeOfWage = setter.wageSetter();
        Schedule schedule = setter.scheduleSetter();

        Program.getUserMap().put(user.getUsername(), new Delivery(user.getUsername(), user.getPassword(), vehicle, typeOfWage, schedule));
    }


    private ArrayList<Item> createMenu(){
        ArrayList<Item> menu = new ArrayList<>();
        System.out.println("\nCreate the Menu: ");

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

    private ArrayList<Item> createItemList() {
        ArrayList<Item> list = new ArrayList<>();
        System.out.println("\nCreate the Item List: ");

        int choice = 0;
        while (choice != 4){
            cli.getOutput().getMenu().printTheItemListMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    list.add(createItem());
                    break;
                case 2:
                    removeItem(list);
                    break;
                case 3:
                    cli.getOutput().printArrayInOrder(list, false);
                    break;
                case 4:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }

        return list;
    }

    private Item createItem() {
        System.out.print("Name of the Item: ");
        String name = cli.input();
        System.out.print("How much of this item is available? ");
        int quantity = cli.inputInt();
        System.out.print("Price of each item: ");
        int price = cli.inputInt();

        return new Item(name, quantity, price, true);
    }

    private void removeItem(ArrayList<Item> menu) {
        if (menu.size()==0){
            System.out.println("There is nothing to remove!");
            return;
        }

        System.out.println("Please select index of item you want to remove: ");
        cli.getOutput().printArrayInOrder(menu, false);
        int choice = cli.inputInt()-1;
        System.out.print(">> ");
        if (choice == menu.size()){
            return;
        }
        menu.remove(choice);
    }

    private Item createFood(){
        System.out.print("Name of the Food: ");
        String name = cli.input();
        System.out.print("How long will it take to prepare item (minutes): ");
        int elapsedTime = cli.inputInt();
        System.out.print("Price of the item (IRR): ");
        int price = cli.inputInt();

        return new Item(name, elapsedTime, price);
    }
}
