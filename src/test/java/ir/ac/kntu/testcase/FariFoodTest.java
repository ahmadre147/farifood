package ir.ac.kntu.testcase;

import ir.ac.kntu.Program;
import ir.ac.kntu.SignIn;
import ir.ac.kntu.type.Duration;
import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.type.Rate;
import ir.ac.kntu.users.Store;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.delivery.Schedule;
import ir.ac.kntu.users.delivery.TypeOfWage;
import ir.ac.kntu.users.delivery.Vehicle;
import ir.ac.kntu.users.restaurant.Item;
import ir.ac.kntu.users.restaurant.Range;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.util.Preload;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class FariFoodTest {
    @Test
    public void loginTest() {
        new Preload();
        SignIn signer = new SignIn();

        Program.getUserMap().keySet().forEach(key -> Assert.assertTrue(signer.login(key, Program.getUserMap().get(key).getPassword())));
    }

    @Test
    public void editConfigTest(){
        Preload.removePreload();
        Restaurant restaurant = new Restaurant("restaurant", new HashPassword("123"), "Kababi Khafan", "tehran", new Duration("10:00 - 2:00"), Range.ECONOMIC, new ArrayList<>(), 23, 11);
        Restaurant theOtherRestaurant = new Restaurant("restaurant", new HashPassword("123"), "Khafan Kabab", "Tehran", new Duration("11:00 - 3:00"), Range.LUXURY, new ArrayList<>(), 23, 10);

        restaurant.setName("Khafan Kabab");
        restaurant.setAddress("Tehran");
        restaurant.setHours(new Duration("11:00-3:00"));
        restaurant.setPriceRange(Range.LUXURY);
        restaurant.setDeliveryPayoutByHour(10);

        Assert.assertEquals(restaurant, theOtherRestaurant);
    }

    @Test
    public void orderTest(){
        Preload.removePreload();
        Customer customer = new Customer("customer1", new HashPassword("123"), "09121111111", "tehranpars");
        Program.getUserMap().put("customer", customer);

        ArrayList<Item> foods = new ArrayList<>();
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        foods.add(new Item("kabab", 0, 10));
        foods.add(new Item("ghorme", 0, 10));
        Restaurant restaurant = new Restaurant("restaurant", new HashPassword("123"), "Kababi Sootoon", "tehran", new Duration("10:00 - 2:00"), Range.ECONOMIC, foods, 23, 11);
        restaurantList.add(restaurant);
        Program.getRestaurantMap().put(restaurant, restaurantList);

        Program.getDeliveryMap().put(new Delivery("delivery1", new HashPassword("123"), Vehicle.BIKE, TypeOfWage.HOURLY, new Schedule(true, true, true, true, true, false, false)), new ArrayList<>(restaurantList));

        Order order = new Order(restaurant, foods, customer.calculateEpTime(foods), customer.calculateCost(foods), true);
        Program.getOrderMap().put(customer, order);

        customer.updateHistory();

        Assert.assertEquals(Program.getOrderHistoryMap().get(customer).get(0), order);
    }

    @Test
    public void deliveryTest(){
        Preload.removePreload();
        Delivery delivery = new Delivery("delivery1", new HashPassword("123"), Vehicle.BIKE, TypeOfWage.HOURLY, new Schedule(true, true, true, true, true, false, false));
        Restaurant restaurant = new Restaurant("restaurant", new HashPassword("123"), "Kababi Sootoon", "tehran", new Duration("10:00 - 2:00"), Range.ECONOMIC, new ArrayList<>(), 23, 11);

        ArrayList<Store> deliverList = new ArrayList<>();
        deliverList.add(restaurant);
        Program.getDeliveryMap().put(delivery, deliverList);

        Assert.assertEquals(restaurant, Program.getDeliveryMap().get(delivery).get(0));
    }

    @Test
    public void ratingTest(){
        Preload.removePreload();
        Customer customer = new Customer("customer1", new HashPassword("123"), "09121111111", "tehranpars");
        Program.getUserMap().put("customer", customer);

        ArrayList<Item> foods = new ArrayList<>();
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        foods.add(new Item("kabab", 0, 10));
        foods.add(new Item("ghorme", 0, 10));
        Restaurant restaurant = new Restaurant("restaurant", new HashPassword("123"), "Kababi Sootoon", "tehran", new Duration("10:00 - 2:00"), Range.ECONOMIC, foods, 23, 11);
        restaurantList.add(restaurant);
        Program.getRestaurantMap().put(restaurant, restaurantList);

        Program.getDeliveryMap().put(new Delivery("delivery1", new HashPassword("123"), Vehicle.BIKE, TypeOfWage.HOURLY, new Schedule(true, true, true, true, true, false, false)), new ArrayList<>(restaurantList));

        Order order = new Order(restaurant, foods, customer.calculateEpTime(foods), customer.calculateCost(foods), true);
        Program.getOrderMap().put(customer, order);

        order.getStore().setRating(new Rate(customer, 4, "khafan"));

        Assert.assertEquals(4, order.getStore().getRating().getOverallScore());
        Assert.assertEquals(1, order.getStore().getRating().getNumOfScores());
        Assert.assertEquals("khafan", order.getStore().getRating().getComments().get(0));
    }
}
