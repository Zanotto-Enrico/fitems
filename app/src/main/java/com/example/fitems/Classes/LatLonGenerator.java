package com.example.fitems.Classes;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LatLonGenerator {

    /**
     * Metodo avente il compito di convertire l'indirizzo passato sotto forma di stringa in coordinate
     * latitudine e longitudine in formato double
     * @param address l'indirzzo da convertire
     * @param c contesto in cui l'applicativo si ritrova al momento della chiamata
     * @return <code>Pair{@literal <}Double, Double></code> nell'ordine contenente longitudine e latitudine
     * @throws IOException in caso di fallimento del metodo di localizzazione
     */
    public static Pair<Double, Double> getCoordinatesFromAddress(String address, Context c) throws IOException {
        Geocoder geocoder = new Geocoder(c);
        List<Address> addresses = geocoder.getFromLocationName(address, 1);
        Address a = addresses.get(0);

        return new Pair<>(a.getLongitude(), a.getLatitude());
    }

    /**
     * Metodo avente il compito di risolvere le coordinate passate come parametro al metodo nel
     * corrispettivo indirizzo sotto forma di stringa
     * @param latitudine
     * @param longitudine
     * @param c contesto in cui l'applicativo si ritrova al momento della chiamata
     * @return stringa contenente l'indirizzo trovato
     * @throws IOException in caso di fallimento del metodo di localizzazione
     */
    public static String getAddressFromCoordinates(double latitudine, double longitudine, Context c) throws IOException {
        Geocoder geocoder = new Geocoder(c, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        addresses = geocoder.getFromLocation(latitudine, longitudine, 1);

        return (!addresses.isEmpty()) ? addresses.get(0).getAddressLine(0).toString() : "";
    }
}
