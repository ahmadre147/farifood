package ir.ac.kntu.users;

import ir.ac.kntu.Program;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.type.HashPassword;

import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends User{
    private CLI cli = new CLI();

    public Admin(){
        super("admin", new HashPassword("@DM1N"));
    }

    @Override
    public void handleUser(){
        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printAdminMenu();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    showOrders(Program.getOrderMap());
                    break;
                case 2:
                    changeOrders(Program.getOrderMap(), Program.getDeliveryMap());
                    break;
                case 3:
                    showUsers(Program.getUserMap());
                    break;
                case 4:
                    configUsers();
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    private void configUsers() {
        ArrayList<User> users = new ArrayList<>();
        System.out.println("Please select a user");
        int i = 1;
        for (User user : Program.getUserMap().values()){
            users.add(user);
            System.out.println(i + ": " + user);
            i++;
        }
        System.out.print(">> ");
        User theChosenOne = users.get(cli.inputInt()-1);
        System.out.print("If you want to remove the user enter 1: ");
        if (cli.input().equals("1")){
            if (theChosenOne instanceof Admin){
                System.out.println("You can't remove admin lol");
                return;
            }
            Program.getUserMap().remove(theChosenOne);
            return;
        }
        theChosenOne.handleUser();
    }

    private void showUsers(HashMap<String, User> userMap) {
        cli.getOutput().printArrayInOrder(new ArrayList<>(userMap.values()), false);
    }

    private void changeOrders(HashMap<Customer, Order> orderMap, HashMap<Delivery, ArrayList<Store>> deliveryMap) {
        if (orderMap.size()==0){
            System.out.println("There is no order yet!");
            return;
        }
        ArrayList<Order> orders = new ArrayList<>();
        System.out.println("Please select an order");
        int i = 1;
        for (Order order : orderMap.values()){
            orders.add(order);
            System.out.println(i + ": " + order);
            i++;
        }
        Order theChosenOne = orders.get(cli.inputInt()-1);

        int choice = 0;
        while (choice != 5){
            cli.getOutput().getMenu().printOrderSettings();
            choice = cli.inputInt();

            switch (choice){
                case 1:
                    theChosenOne.removeFood();
                    break;
                case 2:
                    theChosenOne.changeElapsedTime();
                    break;
                case 3:
                    theChosenOne.deliverySetter();
                    break;
                case 4:
                    orderMap.remove(theChosenOne);
                    break;
                case 5:
                    break;
                default:
                    cli.getOutput().getError().error101();
                    break;
            }
        }
    }

    private void showOrders(HashMap<Customer, Order> orderMap) {
        cli.getOutput().printArrayInOrder(new ArrayList<>(orderMap.values()), false);
    }

    @Override
    public void configUser() {
    }
}
