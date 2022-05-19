package ir.ac.kntu.users.restaurant;

import ir.ac.kntu.type.Rate;

import java.util.Objects;

public class Item {
    private String name;

    private int elapsedTime;

    private int quantity = -1;

    private Rate rate;

    private int price;

    public Item(String name, int elapsedTime, int price) {
        this.name = name;
        this.elapsedTime = elapsedTime;
        this.price = price;
        rate = new Rate();
    }

    public Item(String name, int quantity, int price, boolean separator) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        rate = new Rate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item food = (Item) o;
        return Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        if (quantity == -1) {
            return name + ", Preparing time: " + elapsedTime + " minutes" +
                    ", rating: " + rate +
                    ", price: " + price + " IRR";
        }

        return name + ", available: " + quantity +
                ", rating: " + rate +
                ", price: " + price + " IRR";
    }
}
