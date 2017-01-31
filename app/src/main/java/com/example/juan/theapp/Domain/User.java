package com.example.juan.theapp.Domain;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.juan.theapp.Data.AppDB;

public class User {
    private static User currentUser;

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

    public void setProfileImage(Uri uri) {
        profileImage = uri;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logIn(Context context, String userName, String password) {
        AppDB appDB = new AppDB(context);
        Log.v("user", userName + " " + password);
        currentUser = appDB.getAllUsers().get(0);
        appDB.close();
    }
}
