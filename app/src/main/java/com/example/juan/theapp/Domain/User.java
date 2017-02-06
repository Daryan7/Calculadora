package com.example.juan.theapp.Domain;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.juan.theapp.Data.AppDB;

import java.io.Serializable;

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

    public void setPoints(int points) {
        this.points = points;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean registerIfNotExist(Context context, String userName, long id) {
        AppDB appDB = new AppDB(context);
        currentUser = appDB.getUserWithId(id);
        boolean userNull = currentUser == null;
        if (userNull) {
            currentUser = new User(id, userName, -1, null);
            appDB.registerUser(currentUser);
        }
        appDB.close();
        return userNull;
    }

    public static void logOut() {
        currentUser = null;
    }

    public static void logIn(Context context, long id) {
        AppDB appDB = new AppDB(context);
        currentUser = appDB.getUserWithId(id);
        appDB.close();
    }
}
