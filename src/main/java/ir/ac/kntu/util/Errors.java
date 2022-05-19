package ir.ac.kntu.util;

public class Errors {
    public Errors(){
    }

    public void error001(){
        System.out.println("Please enter an integer.");
    }

    public void error101(){
        System.out.println("Invalid Option. Please try again.");
    }

    public void error102(){
        System.out.println("Login credentials are incorrect. Please try again.");
    }

    public void error103(){
        System.out.println("You don't have any rating yet!");
    }

    public void error104(){
        System.out.println("Invalid input. Please stay on the options.");
    }

    public void error105(String str) {
        System.out.println(str+" format is wrong. Please try again as exampled.");
    }

    public void error106(){
        System.out.println("There is a non-allowed character in you address. Please try again.");
    }

    public void error107(){
        System.out.println("Username at least must be 6 length. It must start with a character and only lowercase characters and numbers are allowed to use.");
    }

    public void error108(){
        System.out.println("This username already exists!");
    }

    public void error109(){
        System.out.print("Password (Must contain at least one uppercase, lowercase and number. Minimum 8 characters): ");
    }

    public void error201(){
        System.out.println("You can't change name of the Chain at this branch.");
    }

    public void error202(){
        System.out.println("You reached your maximum capacity of serving restaurants. " +
                "If you want to add more restaurants you have to remove from current services.");
    }

    public void error203(){
        System.out.println("You do not serve any restaurant!");
    }

    public void error204(){
        System.out.println("This user has no past orders!");
    }

    public void error205(){
        System.out.println("You currently have no order running!");
    }

    public void error206(){
        System.out.println("No delivery available right now. Either try again later or set a pick up order.");
    }
}
