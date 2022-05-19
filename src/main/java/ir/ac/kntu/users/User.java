package ir.ac.kntu.users;

import ir.ac.kntu.util.CLI;
import ir.ac.kntu.util.Checker;
import ir.ac.kntu.users.customer.Customer;
import ir.ac.kntu.users.customer.Order;
import ir.ac.kntu.users.delivery.Delivery;
import ir.ac.kntu.users.restaurant.Restaurant;
import ir.ac.kntu.type.HashPassword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class User {
    private String username;

    private HashPassword password;

    private Checker checker = new Checker();

    private CLI cli = new CLI();

    public User(String username, HashPassword password) {
        this.username = username;
        this.password = password;
    }

    public User(){
    }

    public String getUsername() {
        return username;
    }

    public HashPassword getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public void handleUser(){

    }

    public void showUser(){
        System.out.println(this);
    }

    public void configUser(){}

    public void changePassword() {
        System.out.print("New Password: ");
        String password = cli.input();
        while (!checker.checkPassword(password)){
            cli.getOutput().getError().error109();
            System.out.print("New Password: ");
            password = cli.input();
        }
        this.password.setPassword(password);
    }

    @Override
    public String toString() {
        return "Username: " + username +
                ", Type: " + getClass().getSimpleName();
    }
}
