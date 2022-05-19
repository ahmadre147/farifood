package ir.ac.kntu.util;

import ir.ac.kntu.type.HashPassword;
import ir.ac.kntu.users.Store;

import java.io.EOFException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI {
    private Printer output;

    private Scanner in;

    public CLI(){
        output = new Printer();
        in = new Scanner(System.in);
    }

    public String input(){
        return in.nextLine();
    }

    public Printer getOutput(){
        return output;
    }

    public int inputInt() {
        int out;
        try {
            out = Integer.parseInt(in.nextLine().strip());
        } catch (Exception e){
            output.getError().error001();
            System.out.print(">> ");
            return inputInt();
        }

        return out;
    }

    public String usernameGetter(){
        System.out.print("Username: ");
        return input();
    }

    public HashPassword passwordGetter(){
        System.out.print("Password: ");
        return new HashPassword(input().strip());
    }


    public void showStoreReviews(Store store) {
        if (store.getRating()==null || store.getRating().getOverallScore()==0){
            output.getError().error103();
            return;
        }
        System.out.println("Your overall score = " + store.getRating().getOverallScore());
        System.out.println("Comments:");
        store.getRating().printComments();
    }

    public boolean inputBoolean() {
        String input = input().strip();
        if (input.equals("Y") || input.equals("y")){
            return true;
        }
        return false;
    }
}
