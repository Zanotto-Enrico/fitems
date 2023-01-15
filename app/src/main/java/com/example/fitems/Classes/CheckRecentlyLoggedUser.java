package com.example.fitems.Classes;

import android.content.SharedPreferences;
import android.util.Pair;

/**
 * Classe utile per permettere all'utente di ovviare al processo di login se tale
 * procedimento era già stato da lui svolto in un tempo inferiore a 14 giorni
 */
public class CheckRecentlyLoggedUser {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final int VALID_DAYS = 14;

    public CheckRecentlyLoggedUser(SharedPreferences sp) {
        this.sp = sp;
        this.editor = this.sp.edit();
    }

    /**
     * Metodo utile a verificare se un utente sia loggato nel sistema, ovvero se il suo ultimo login
     * sia stato effettuato ad una distanza inferiore a 14 giorni
     * @return true: se verificato quanto precisato<br>false: se l'utente ha superato i 14 giorni di tempo (in questo caso l'utente si troverà anche automaticamente sloggato dal sistema
     */
    public boolean isLoginValid() {
        if (this.sp.getBoolean("isLogged", false) &&
            MyDate.getToday().getDateIdentificator() - this.sp.getLong("loginDay", 0) <= this.VALID_DAYS
           )
            return true;

        this.logOut();
        return false;
    }

    /**
     * Metodo per registrare il login dell'utente al sistema
     * @param user username da lui inserita in fase di login
     * @param password password da lui inserita in fase di login
     * @param day giorno in cui si è eseguito il login
     */
    public void logIn(String user, String password, MyDate day) {
        editor.putString("username", user);
        editor.putString("password", password);
        editor.putLong("loginDay", day.getDateIdentificator());
        editor.putBoolean("isLogged", true);
        editor.apply();
    }

    /**
     * Metodo utilizzato per attuare in maniera pratica il logout dell'utente dal sistema
     */
    public void logOut() {
        editor.putBoolean("isLogged", false);
        editor.apply();
    }

    /**
     * Metodo utilizzato per restituire le informazioni dell'utente attualmente loggato all'interno dell'applicativo
     * @return <code>Pair</code> value in cui, in prima posizione sarà visibile lo username dell'utente e in seconda posizione la sua password inserita
     */
    public Pair<String, String> getInfoUserLogged() {
        if(this.isLoginValid())
            return new Pair<>(this.sp.getString("username", ""), this.sp.getString("password", ""));
        return null;
    }
}
