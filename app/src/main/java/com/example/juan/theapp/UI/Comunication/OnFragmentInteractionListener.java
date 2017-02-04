package com.example.juan.theapp.UI.Comunication;

import com.example.juan.theapp.Data.AppDB;

public interface OnFragmentInteractionListener {
    void updateUser();
    AppDB getDataBase();
    boolean checkPermissions(String permission);
}
