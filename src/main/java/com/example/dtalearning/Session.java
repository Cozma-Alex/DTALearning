package com.example.dtalearning;


import com.example.dtalearning.Domain.User;

public class Session {
    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static void clearSession() {
        loggedUser = null;
    }

    public static boolean isLoggedIn() {
        return loggedUser != null;
    }
}
