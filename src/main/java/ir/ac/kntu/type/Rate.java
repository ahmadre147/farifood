package ir.ac.kntu.type;

import ir.ac.kntu.users.customer.Customer;

import java.util.ArrayList;

public class Rate {
    private Customer customer;

    private int overallScore = 0;

    private int numOfScores = 0;

    private ArrayList<String> comments = new ArrayList<>();

    public Rate(Customer customer, int score, String comment) {
        this.customer = customer;
        setScore(score);
        comments.add(comment);
    }

    public Rate(){
    }

    public void setScore(int score) {
        if (numOfScores==0){
            overallScore = score;
            numOfScores++;
            return;
        }

        overallScore = (overallScore * (numOfScores++) + score) / numOfScores;
    }

    public void printComments() {
        for (String comment : comments){
            System.out.println(customer.getUsername() + ": " + comment);
        }
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public int getNumOfComments(){
        return comments.size();
    }

    public void setComment(String comment) {
        comments.add(comment);
    }

    public int getOverallScore() {
        return overallScore;
    }

    public int getNumOfScores(){
        return numOfScores;
    }

    public int compareByRate(Rate rating) {
        if (rating!=null && overallScore>rating.getOverallScore()){
            return 1;
        }
        return -1;
    }

    public int compareByNumOfRate(Rate rating) {
        if (rating!=null && numOfScores>rating.getNumOfComments()){
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return ""+overallScore;
    }
}
