package com.example.juan.calculadora.Data;

public class User {
    static User user;

    private String nick;
    private long id;
    private int points;

    User(long id, String nick, int points) {
        this.nick = nick;
        this.id = id;
        this.points = points;
    }

    public static User getCurrentUser() {
        return user;
    }
}
