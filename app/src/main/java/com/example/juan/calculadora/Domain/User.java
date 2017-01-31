package com.example.juan.calculadora.Domain;

import android.net.Uri;

import java.net.URI;

public class User {
    static User currentUser;

    private String nick;
    private long id;
    private int points;
    private Uri profileImage;

    public User(long id, String nick, int points, Uri profileImage) {
        this.profileImage = profileImage;
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
