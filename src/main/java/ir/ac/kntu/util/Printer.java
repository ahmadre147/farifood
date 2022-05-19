package ir.ac.kntu.util;

import java.util.ArrayList;

public class Printer {
    private Menus menu;

    private Errors error;

    public Printer(){
        menu = new Menus();
        error = new Errors();
    }

    public Errors getError() {
        return error;
    }

    public Menus getMenu() {
        return menu;
    }

    public void printArrayInOrder(ArrayList<?> list, boolean condition){
        int i=1;
        if (list.size()==0){
            System.out.println("There is nothing to display!");
            return;
        }
        for (Object object : list){
            System.out.println(i + ": " + object);
            i++;
        }
        if (condition){
            System.out.print(">> ");
        }
    }
}
