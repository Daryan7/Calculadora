package com.example.juan.calculadora.Domain;

public class User {
    private static User currentUser;

    private String nick;
    private long id;
    private int points;

    public User(long id, String nick, int points) {
        this.nick = nick;
        this.id = id;
        this.points = points;
    }

    public String getNickName() {
        return nick;
    }

    public long getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
