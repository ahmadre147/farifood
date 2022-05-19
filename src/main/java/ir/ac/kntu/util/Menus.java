package ir.ac.kntu.util;

public class Menus {

    public Menus(){
    }

    public void printScheduleSettingHelp() {
        System.out.println("\nSchedule availability format should be like XXXXXXX (7 A or N) .\n" +
                "Each X representing availability on a single day of a week.\n" +
                "The week starts at saturday and ends at friday (Standard Persian Calendar format).\n" +
                "Use A as \"Available\" and N as \"Not Available\".\n" +
                "e.g. \"AANNANA\" would be interpreted as available on everyday except monday, tuesday and thursday.\n");
    }

    public void printSignupMenu(){
        System.out.println("Sign Up as:");
        System.out.println("1. Customer");
        System.out.println("2. Store or Restaurant owner");
        System.out.println("3. Delivery Service");
        System.out.println("4. Return to the main menu");
        System.out.print("\r\n>>> ");
    }

    public void printCustomerMenu(){
        System.out.println("\n***************");
        System.out.println("1. Show Profile");
        System.out.println("2. Order");
        System.out.println("3. Order situation");
        System.out.println("4. Order history");
        System.out.println("5. Rate orders");
        System.out.println("6. Change profile information");
        System.out.println("7. Return");
        System.out.println("***************");
        System.out.print("\r\n>>> ");
    }


    public void printTheMenuMenu() {
        System.out.println("\n1. Create food");
        System.out.println("2. Remove food");
        System.out.println("3. See menu");
        System.out.println("4. Done");
        System.out.print("\r\n>>> ");
    }

    public void printRestaurantMenu(){
        System.out.println("\n***************");
        System.out.println("1. Show Profile");
        System.out.println("2. Add a new restaurant branch");
        System.out.println("3. Edit specifications of an existing restaurant");
        System.out.println("4. Number of existing delivery services");
        System.out.println("5. User reviews");
        System.out.println("6. Return to main menu");
        System.out.println("***************");
        System.out.print("\r\n>>> ");
    }


    public void printEditRestaurantMenu() {
        System.out.println("1. Change name of the store");
        System.out.println("2. Change address");
        System.out.println("3. Change working hours");
        System.out.println("4. Change claimed price range");
        System.out.println("5. Change in list of the Items");
        System.out.println("6. Return");
        System.out.print("\r\n>>> ");
    }

    public void printEditStoreMenu() {
        System.out.println("1. Change name of the store");
        System.out.println("2. Change address");
        System.out.println("3. Change working hours");
        System.out.println("4. Change in list of the Items");
        System.out.println("5. Return");
        System.out.print("\r\n>>> ");
    }

    public void printDeliveryMenu(){
        System.out.println("\n***************");
        System.out.println("1. Show Profile");
        System.out.println("2. Manage services");
        System.out.println("3. Show user reviews and rating");
        System.out.println("4. History of deliveries");
        System.out.println("5. Change profile information");
        System.out.println("6. Return");
        System.out.println("***************");
        System.out.print("\r\n>>> ");
    }

    public void printChangeDeliveryInfoMenu() {
        System.out.println("1. Change vehicle");
        System.out.println("2. Change type of preferred wage");
        System.out.println("3. Change Schedule");
        System.out.println("4. Change Password");
        System.out.println("5. Return");
        System.out.print("\r\n>>> ");
    }

    public void printDeliveryServiceManager() {
        System.out.println("1. Add Service");
        System.out.println("2. Remove Service");
        System.out.println("3. Return");
        System.out.print("\r\n>>> ");
    }

    public void printMainMenu(){
        System.out.println("\n***************");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.println("***************");
        System.out.print("\r\n>>> ");
    }

    public void printSortRestaurantMenu() {
        System.out.println("How do you want the list of restaurants to be shown?");
        System.out.println("1. Descending by rating");
        System.out.println("2. Ascending by rating");
        System.out.println("3. Descending by number of ratings");
        System.out.println("4. Ascending by number of ratings");
        System.out.print("\r\n>>> ");
    }

    public void printAdminMenu() {
        System.out.println("1. Show all of running orders");
        System.out.println("2. Change state of running orders");
        System.out.println("3. Show all users");
        System.out.println("4. Remove or change user info");
        System.out.println("5. Return to main menu");
        System.out.print("\r\n>>> ");
    }

    public void printOrderSettings(){
        System.out.println("1. Remove a food");
        System.out.println("2. Change remaining time");
        System.out.println("3. Change delivery");
        System.out.println("4. Remove order");
        System.out.println("5. Return to admin menu");
        System.out.print("\r\n>>> ");
    }

    public void printConfigCustomerMenu() {
        System.out.println("1. Change password");
        System.out.println("2. Change phone number");
        System.out.println("3. Change address");
        System.out.println("4. Return to customer menu");
        System.out.print("\r\n>>> ");
    }

    public void printGroceryMenu() {
        System.out.println("\n***************");
        System.out.println("1. Show Profile");
        System.out.println("2. Edit specifications of the store");
        System.out.println("3. Number of existing delivery services");
        System.out.println("4. User reviews");
        System.out.println("5. Return to main menu");
        System.out.println("***************");
        System.out.print("\r\n>>> ");
    }

    public void printUserTypeSetterMenu() {
        System.out.println("\nSign up as:");
        System.out.println("1. Restaurant (Chain)");
        System.out.println("2. Market");
        System.out.println("3. Grocery Store");
        System.out.println("4. Return to sign up menu");
        System.out.print("\r\n>>> ");
    }

    public void printTheItemListMenu() {
        System.out.println("\n1. Create new item");
        System.out.println("2. Remove existing item");
        System.out.println("3. See the Items List");
        System.out.println("4. Done");
        System.out.print("\r\n>>> ");
    }

    public void printConfigItemListMenu() {
        System.out.println("\n1. Create new item");
        System.out.println("2. Remove existing item");
        System.out.println("3. See the Items List");
        System.out.println("4. Change specifications of an existing item (name, quantity, price)");
        System.out.println("5. Return");
        System.out.print("\r\n>>> ");
    }

    public void printConfigItemMenu() {
        System.out.println("\n1. Change name of the Item");
        System.out.println("2. Change quantity of the Item");
        System.out.println("3. Change fee of the Item");
        System.out.println("4. Remove the Item");
        System.out.println("5. Return");
        System.out.print("\r\n>>> ");
    }

    public void printOrderTypeMenu() {
        System.out.println("\nOrder from: ");
        System.out.println("1. Restaurant");
        System.out.println("2. Market or Grocery Store");
        System.out.println("3. Return");
        System.out.print("\r\n>>> ");
    }
}
