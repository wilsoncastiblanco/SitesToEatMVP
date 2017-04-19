package com.example.sitestoeat.model;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class User {
    private String userName;
    private String password;
    private String names;


    public User(String userName, String password, String names) {
        this.userName = userName;
        this.password = password;
        this.names = names;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getNames() {
        return names;
    }
}
