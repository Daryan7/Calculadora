package com.example.juan.myapp.Domain;

import android.net.Uri;

public class User {
    private static User currentUser = new User(0, "Daryan", 15, null);

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

    public boolean hasProfilePic() {
        return profileImage != null;
    }

    public Uri getProfileImage() {
        return profileImage;
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
