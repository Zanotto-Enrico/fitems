package com.example.fitems.Classes;

import android.content.SharedPreferences;
import android.util.Pair;

public class CheckRecentlyLoggedUser {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final int VALID_DAYS = 14;

    public CheckRecentlyLoggedUser(SharedPreferences sp) {
        this.sp = sp;
        this.editor = this.sp.edit();
    }

    public boolean isLoginValid() {
        if (this.sp.getBoolean("isLogged", false) &&
            MyDate.getToday().getDateIdentificator() - this.sp.getLong("loginDay", 0) <= this.VALID_DAYS
           )
            return true;

        this.logOut();
        return false;
    }

    public void logIn(String user, String password, MyDate day) {
        editor.putString("username", user);
        editor.putString("password", password);
        editor.putLong("loginDay", day.getDateIdentificator());
        editor.putBoolean("isLogged", true);
        editor.apply();
    }

    public void logOut() {
        editor.putBoolean("isLogged", false);
        editor.apply();
    }

    public Pair<String, String> getInfoUserLogged() {
        if(this.isLoginValid())
            return new Pair<>(this.sp.getString("username", ""), this.sp.getString("password", ""));
        return null;
    }
}
