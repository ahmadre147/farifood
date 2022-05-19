package ir.ac.kntu;

import ir.ac.kntu.users.User;
import ir.ac.kntu.util.CLI;
import ir.ac.kntu.type.HashPassword;

import java.util.HashMap;

public class SignIn {
    private CLI cli = new CLI();

    private final HashMap<String, User> userMap;

    public SignIn(){
        userMap = Program.getUserMap();
    }

    public void login() {
        String username = cli.usernameGetter();
        HashPassword password = cli.passwordGetter();

        if (userMap.containsKey(username) && userMap.get(username).getPassword().equals(password)){
            userMap.get(username).handleUser();
        } else {
            cli.getOutput().getError().error102();
        }
    }

    public boolean login(String username, HashPassword password){
        return userMap.containsKey(username) && userMap.get(username).getPassword().equals(password);
    }
}
