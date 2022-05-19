package ir.ac.kntu.util;

import ir.ac.kntu.Program;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.users.User;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.delivery.Schedule;
import ir.ac.kntu.users.delivery.TypeOfWage;
import ir.ac.kntu.users.delivery.Vehicle;
import ir.ac.kntu.users.grocery.Grocery;
import ir.ac.kntu.users.market.Market;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Range;
import ir.ac.kntu.users.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;

public class Preload {
    public Preload(){
        HashMap<String, User> userMap = Program.getUserMap();
        HashMap<Restaurant, ArrayList<Restaurant>> restaurantMap = Program.getRestaurantMap();

        // Customer samples
        userMap.put("customer1", new Customer("customer1", new HashPassword("123"), "09121111111", "tehranpars"));
        userMap.put("customer2", new Customer("customer2", new HashPassword("123"), "09121111111", "tajrish"));
        userMap.put("customer3", new Customer("customer3", new HashPassword("123"), "09121111111", "qolhak"));

        // Delivery samples
        userMap.put("delivery1", new Delivery("delivery1", new HashPassword("123"), Vehicle.BIKE, TypeOfWage.HOURLY, new Schedule(true, true, true, true, true, false, false)));
        userMap.put("delivery2", new Delivery("delivery2", new HashPassword("123"), Vehicle.CAR, TypeOfWage.HOURLY, new Schedule(true, true, true, true, false, true, false)));
        userMap.put("delivery3", new Delivery("delivery3", new HashPassword("123"), Vehicle.BIKE, TypeOfWage.PER_ORDER, new Schedule(true, false, false, true, true, false, true)));
        userMap.put("delivery4", new Delivery("delivery4", new HashPassword("123"), Vehicle.CAR, TypeOfWage.PER_ORDER, new Schedule(true, true, true, false, true, false, true)));
        userMap.put("delivery5", new Delivery("delivery5", new HashPassword("123"), Vehicle.BIKE, TypeOfWage.HOURLY, new Schedule(true, true, false, true, false, true, false)));
        userMap.put("delivery6", new Delivery("delivery6", new HashPassword("123"), Vehicle.CAR, TypeOfWage.PER_ORDER, new Schedule(false, false, true, true, true, true, false)));

        // Grocery samples
        userMap.put("grocery1", new Grocery("grocery1", new HashPassword("123"), "Village Grocer", "tehran", new Duration("8:00 - 00:00"), new Duration("10:00 - 18:00"), new ArrayList<>(), 12, 20));
        userMap.put("grocery2", new Grocery("grocery2", new HashPassword("123"), "Amme Leila Grocery Store", "tehran", new Duration("10:00 - 18:00"), new Duration("10:00 - 16:00"), new ArrayList<>(), 13, 22));
        userMap.put("grocery3", new Grocery("grocery3", new HashPassword("123"), "Haji Arzooni", "tehran", new Duration("9:00 - 22:00"), new Duration("10:00 - 20:00"), new ArrayList<>(), 10, 14));

        // Market samples
        userMap.put("market1", new Market("market1", new HashPassword("123"), "Asghar Market", "tehran", new Duration("8:00 - 00:00"), new Duration("10:00 - 18:00"), new ArrayList<>(), 12, 20));
        userMap.put("market2", new Market("market2", new HashPassword("123"), "Daryani Market", "tabriz", new Duration("10:00 - 18:00"), new Duration("10:00 - 16:00"), new ArrayList<>(), 13, 22));
        userMap.put("market3", new Market("market3", new HashPassword("123"), "Star Market", "tehran", new Duration("9:00 - 22:00"), new Duration("10:00 - 20:00"), new ArrayList<>(), 10, 14));

        // Restaurant samples
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant("restaurant1", new HashPassword("123"), "Kil Restaurant", "tehran", new Duration("11:00 - 20:00"), Range.ECONOMIC, new ArrayList<>(), 22, 11);
        restaurantList.add(new Restaurant("restaurant1", new HashPassword("123"), "Kil Restaurant", "tehran", new Duration("11:00 - 20:00"), Range.ECONOMIC, new ArrayList<>(), 22, 11));
        userMap.put("restaurant1", restaurant1);
        restaurantMap.put(restaurant1, restaurantList);
        restaurantList = new ArrayList<>();

        Restaurant restaurant2 = new Restaurant("restaurant2", new HashPassword("123"), "Mammad Restaurant", "tehran", new Duration("10:00 - 22:00"), Range.MEDIUM, new ArrayList<>(), 24, 13);
        userMap.put("restaurant2", restaurant2);
        restaurantList.add(restaurant2);
        restaurantMap.put(restaurant2, restaurantList);
        restaurantList = new ArrayList<>();

        // This is a chain restaurant
        Restaurant restaurant31 =  new Restaurant("restaurant3", new HashPassword("123"), "Padide Restaurant", "mirdamad", new Duration("11:00 - 00:00"), Range.LUXURY, new ArrayList<>(), 28, 16);
        Restaurant restaurant32 = new Restaurant("Padide sattarkhan", "sattarkhan", new Duration("11:00-00:00"), Range.LUXURY, new ArrayList<>(), 26, 15);
        Restaurant restaurant33 = new Restaurant("Padide vanak", "vanak", new Duration("11:00-00:00"), Range.LUXURY, new ArrayList<>(), 26, 15);
        restaurantList.add(restaurant31);
        restaurantList.add(restaurant32);
        restaurantList.add(restaurant33);
        userMap.put("restaurant3", restaurant31);
        restaurantMap.put(restaurant31, restaurantList);
        restaurantList = new ArrayList<>();

        ArrayList<Item> foods = new ArrayList<>();
        foods.add(new Item("kabab", 1, 10));
        Restaurant restaurant4 = new Restaurant("restaurant4", new HashPassword("123"), "Kababi Sootoon", "tehran", new Duration("10:00 - 2:00"), Range.ECONOMIC, foods, 23, 11);
        restaurantList.add(restaurant4);
        userMap.put("restaurant4", restaurant4);
        restaurantMap.put(restaurant4, restaurantList);
        restaurantList = new ArrayList<>();
    }
    
    public static void removePreload(){
        HashMap<String, User> userMap = Program.getUserMap();
        HashMap<Restaurant, ArrayList<Restaurant>> restaurantMap = Program.getRestaurantMap();

        // Customer samples
        userMap.remove("customer1");
        userMap.remove("customer2");
        userMap.remove("customer3");
        
        // Delivery samples
        userMap.remove("delivery1");
        userMap.remove("delivery2");
        userMap.remove("delivery3");
        userMap.remove("delivery4");
        userMap.remove("delivery5");
        userMap.remove("delivery6");
        
        // Grocery samples
        userMap.remove("grocery1");
        userMap.remove("grocery2");
        userMap.remove("grocery3");

        // Market samples
        userMap.remove("market1");
        userMap.remove("market2");
        userMap.remove("market3");
        
        // Restaurant samples
        ArrayList<Item> foods = new ArrayList<>();
        foods.add(new Item("kabab", 1, 10));
        Restaurant restaurant1 = new Restaurant("restaurant1", new HashPassword("123"), "Kil Restaurant", "tehran", new Duration("11:00 - 20:00"), Range.ECONOMIC, new ArrayList<>(), 22, 11);
        Restaurant restaurant2 = new Restaurant("restaurant2", new HashPassword("123"), "Mammad Restaurant", "tehran", new Duration("10:00 - 22:00"), Range.MEDIUM, new ArrayList<>(), 24, 13);
        Restaurant restaurant3 =  new Restaurant("restaurant3", new HashPassword("123"), "Padide Restaurant", "mirdamad", new Duration("11:00 - 00:00"), Range.LUXURY, new ArrayList<>(), 28, 16);
        Restaurant restaurant4 = new Restaurant("restaurant4", new HashPassword("123"), "Kababi Sootoon", "tehran", new Duration("10:00 - 2:00"), Range.ECONOMIC, foods, 23, 11);

        userMap.remove("restaurant1");
        restaurantMap.remove(restaurant1);
        userMap.remove("restaurant2");
        restaurantMap.remove(restaurant2);
        userMap.remove("restaurant3");
        restaurantMap.remove(restaurant3);
        userMap.remove("restaurant4");
        restaurantMap.remove(restaurant4);
    }
}
