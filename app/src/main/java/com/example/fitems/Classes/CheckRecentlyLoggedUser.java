package com.example.fitems.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import java.util.Date;

public class CheckRecentlyLoggedUser {
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    private final int VALID_DAYS = 14;

    public CheckRecentlyLoggedUser(SharedPreferences sp) {
        this.sp = sp;
        this.editor = this.sp.edit();
    }

    public boolean isLogged() {
        return this.sp.getBoolean("isLogged", false);
    }

    public boolean isLoginValid() {
        if (this.sp.getBoolean("isLogged", false) &&
            MyDate.getToday().getDateIdentificator() - this.sp.getLong("loginDay", 0) <= this.VALID_DAYS
           )
            return true;

        return false;
    }

    public void setAsLoggedOrNot(String user, MyDate day, boolean isLogged) {
        editor.putString("username", user);
        editor.putLong("loginDay", day.getDateIdentificator());
        editor.putBoolean("isLogged", isLogged);
        editor.apply();
    }


}
