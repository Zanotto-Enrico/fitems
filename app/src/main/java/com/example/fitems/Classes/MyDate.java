package com.example.fitems.Classes;

import java.time.LocalDateTime;

/**
 * Classe personale per rappresentare una data nel sistema
 */
public class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Metodo che restituisce la data di oggi nel formato specificato dalla classe Date personale
     * (Giorno, Mese, Anno)
     * @return oggetto <code>MyDate</code> nuovo con le informazioni di oggi impostate
     */
    public static MyDate getToday() {
        return new MyDate(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), LocalDateTime.now().getDayOfMonth());
    }

    /**
     * Metodo che restituisce un identificatore per la data corrente ottenuto come il prodotto tra
     * l'anno, il mese e il giorno della data
     * @return valore di moltiplicazione
     */
    public long getDateIdentificator() {
        return (long) this.year * this.month * this.day;
    }

    @Override
    public String toString() {
        return "MyDate {" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
